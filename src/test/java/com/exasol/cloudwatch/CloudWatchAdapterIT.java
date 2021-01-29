package com.exasol.cloudwatch;

import static com.exasol.cloudwatch.CloudWatchPointWriter.CLUSTER_NAME_DIMENSION_KEY;
import static com.exasol.cloudwatch.CloudWatchPointWriter.DEPLOYMENT_DIMENSION_KEY;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.joda.time.DateTime;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.exasol.containers.ExasolContainer;

import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.*;
import software.amazon.awssdk.services.cloudwatch.paginators.GetMetricDataIterable;

@Testcontainers
// [itest->dsn~env-var-for-metrics-selection~1]
class CloudWatchAdapterIT {

    @Container
    private static final ExasolContainer<? extends ExasolContainer<?>> EXASOL = new ExasolContainer<>("7.0.5")
            .withReuse(true);
    private static final Logger LOGGER = LoggerFactory.getLogger(CloudWatchAdapterIT.class);
    private static Connection connection;
    private static CloudWatchClient cloudWatch;
    private String uniqueDeploymentName;

    @BeforeAll
    static void beforeAll() throws SQLException {
        connection = EXASOL.createConnection();
        cloudWatch = CloudWatchClient.builder().build();
    }

    @AfterAll
    static void afterAll() throws SQLException {
        connection.close();
        cloudWatch.close();
    }

    @BeforeEach
    void beforeEach() {
        this.uniqueDeploymentName = "TEST-" + new Date().getTime() + "-" + ((int) (Math.random() * 1000));
        LOGGER.info("current deployment name: {}", this.uniqueDeploymentName);
    }

    @Test
    void testMetricsAreCreated() throws Exception {
        try (final ExaStatisticsTableMock statisticsTable = new ExaStatisticsTableMock(connection)) {
            final Instant now = Instant.now();
            statisticsTable.addRows(Stream.of(new ExaStatisticsTableMock.Row(
                    now.minus(Duration.ofMinutes(1)).minus(Duration.ofSeconds(1)), "MASTER", 10, 1)));
            runAdapter(ExaStatisticsTableMock.SCHEMA, "QUERIES", now);
            waitForCloudWatchToSync();
            final List<Metric> metrics = listCurrentDeploymentsMetrics();
            final Metric firstMetric = metrics.get(0);
            final List<Dimension> dimensions = firstMetric.dimensions();
            assertAll(//
                    () -> assertThat(metrics.size(), equalTo(1)),
                    () -> assertThat(firstMetric.metricName(), equalTo("QUERIES")),
                    () -> assertThat(firstMetric.namespace(), equalTo("Exasol")), () -> assertThat(dimensions,
                            hasItem(Dimension.builder().name("Cluster Name").value("MASTER").build()))//
            );
        }
    }

    @Test
    void testMetricPointIsPut() throws Exception {
        try (final ExaStatisticsTableMock statisticsTable = new ExaStatisticsTableMock(connection)) {
            final Instant now = Instant.now();
            mockLogs(statisticsTable, now, 5, 0);
            runAdapter(ExaStatisticsTableMock.SCHEMA, "USERS", now);
            waitForCloudWatchToSync();
            final TreeMap<Instant, Double> averageResults = readCloudwatchResultAsTreeMap(
                    buildCloudWatchQueryForLast5Minutes(now, "Average"));
            assertAll(//
                    () -> assertThat(averageResults.size(), equalTo(1)),
                    () -> assertThat(averageResults.firstKey(),
                            equalTo(now.truncatedTo(ChronoUnit.MINUTES).minus(Duration.ofMinutes(1)))),
                    () -> assertThat(averageResults.firstEntry().getValue(), equalTo(1.0))//
            );
        }
    }

    @Test
    // [itest->dsn~report-minute-before-event~1]
    void testIncrementalReport() throws Exception {
        try (final ExaStatisticsTableMock statisticsTable = new ExaStatisticsTableMock(connection)) {
            final Instant now = Instant.now();
            mockLogs(statisticsTable, now, 5, 0);
            runAdapter(ExaStatisticsTableMock.SCHEMA, "USERS", now.minus(Duration.ofMinutes(2)));
            runAdapter(ExaStatisticsTableMock.SCHEMA, "USERS", now.minus(Duration.ofMinutes(1)));
            runAdapter(ExaStatisticsTableMock.SCHEMA, "USERS", now);
            waitForCloudWatchToSync();
            final TreeMap<Instant, Double> sumResults = readCloudwatchResultAsTreeMap(
                    buildCloudWatchQueryForLast5Minutes(now, "Sum"));
            assertAll(//
                    () -> assertThat("no points were reported twice", sumResults.values(), everyItem(equalTo(1.0))),
                    () -> assertThat("new points are reported", sumResults.size(), equalTo(3))//
            );
        }
    }

    private void mockLogs(final ExaStatisticsTableMock statisticsTable, final Instant now,
            final int startInMinutesBeforeNow, final int endInMinutesBeforeNow) {
        statisticsTable
                .addRows(IntStream.range(0, startInMinutesBeforeNow - endInMinutesBeforeNow)
                        .mapToObj(counter -> new ExaStatisticsTableMock.Row(
                                now.minus(Duration.ofSeconds((endInMinutesBeforeNow + counter) * 60L)), "MASTER", 1,
                                1)));
    }

    private GetMetricDataIterable buildCloudWatchQueryForLast5Minutes(final Instant now,
            final String statisticsFunction) {
        final MetricDataQuery metricDataQuery = MetricDataQuery.builder().id("myQuery")
                .metricStat(MetricStat.builder().stat(statisticsFunction).period(60).metric(Metric.builder()
                        .namespace("Exasol").metricName("USERS").dimensions(expectedDimensions()).build()).build())
                .build();
        return cloudWatch.getMetricDataPaginator(GetMetricDataRequest.builder().metricDataQueries(metricDataQuery)
                .startTime(now.minus(Duration.ofMinutes(5))).endTime(now).build());
    }

    private TreeMap<Instant, Double> readCloudwatchResultAsTreeMap(final GetMetricDataIterable metricDataPaginator) {
        final TreeMap<Instant, Double> result = new TreeMap<>();
        for (final GetMetricDataResponse getMetricDataResponse : metricDataPaginator) {
            for (final MetricDataResult metricDataResult : getMetricDataResponse.metricDataResults()) {
                final List<Instant> timestamps = metricDataResult.timestamps();
                final List<Double> values = metricDataResult.values();
                for (int index = 0; index < timestamps.size(); index++) {
                    result.put(timestamps.get(index), values.get(index));
                }
            }
        }
        return result;
    }

    private Dimension[] expectedDimensions() {
        return new Dimension[] {
                Dimension.builder().name(DEPLOYMENT_DIMENSION_KEY).value(this.uniqueDeploymentName).build(),
                Dimension.builder().name(CLUSTER_NAME_DIMENSION_KEY).value("MASTER").build() };
    }

    /**
     * Since cloud watch is not transactional we need to wait after putting values so that the values are available.
     * 
     * @throws InterruptedException if interrupted
     */
    @SuppressWarnings("java:S2925")
    private void waitForCloudWatchToSync() throws InterruptedException {
        final int duration = 10;
        LOGGER.info("Waiting {} seconds for coudwatch to sync.", duration);
        Thread.sleep(duration * 1000);
    }

    private List<Metric> listCurrentDeploymentsMetrics() {
        final ListMetricsRequest listRequest = ListMetricsRequest.builder().dimensions(
                DimensionFilter.builder().name(DEPLOYMENT_DIMENSION_KEY).value(this.uniqueDeploymentName).build())
                .build();
        return cloudWatch.listMetrics(listRequest).metrics();
    }

    private void runAdapter(final String schemaOverride, final String metrics, final Instant forMinute)
            throws Exception {
        withEnvironmentVariable("EXASOL_HOST", EXASOL.getHost())
                .and("EXASOL_PORT", EXASOL.getFirstMappedPort().toString()).and("EXASOL_USER", EXASOL.getUsername())
                .and("EXASOL_PASS", EXASOL.getPassword()).and("EXASOL_DEPLOYMENT_NAME", this.uniqueDeploymentName)
                .and("METRICS", metrics).execute(() -> {
                    final ScheduledEvent event = new ScheduledEvent();
                    event.setTime(new DateTime(forMinute.toEpochMilli()));
                    final CloudWatchAdapter adapter = new CloudWatchAdapter(schemaOverride);
                    adapter.handleRequest(event, mock(Context.class));
                });
    }
}