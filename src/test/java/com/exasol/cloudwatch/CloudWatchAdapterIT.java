package com.exasol.cloudwatch;

import static com.exasol.cloudwatch.CloudWatchPointWriter.CLUSTER_NAME_DIMENSION_KEY;
import static com.exasol.cloudwatch.CloudWatchPointWriter.DEPLOYMENT_DIMENSION_KEY;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.CLOUDWATCH;

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
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

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
    @Container
    private static final LocalStackContainer LOCAL_STACK_CONTAINER = new LocalStackContainer(
            DockerImageName.parse("localstack/localstack:latest")).withServices(CLOUDWATCH);
    private static final Logger LOGGER = LoggerFactory.getLogger(CloudWatchAdapterIT.class);
    private static Connection connection;
    private static CloudWatchClient cloudWatch;
    private String uniqueDeploymentName;
    private static LocalstackCloudWatchBackdoor localstackCloudWatchBackdoor;

    @BeforeAll
    static void beforeAll() throws SQLException {
        connection = EXASOL.createConnection();
        cloudWatch = CloudWatchClient.builder().endpointOverride(LOCAL_STACK_CONTAINER.getEndpointOverride(CLOUDWATCH))
                .build();
        localstackCloudWatchBackdoor = new LocalstackCloudWatchBackdoor(LOCAL_STACK_CONTAINER);
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
            final SortedMap<Instant, Double> writtenPoints = localstackCloudWatchBackdoor.readMetrics("USERS",
                    expectedDimensions());
            assertAll(//
                    () -> assertThat(writtenPoints.size(), equalTo(1)),
                    () -> assertThat("single written point is the one of the previous minute",
                            writtenPoints.firstKey().truncatedTo(ChronoUnit.SECONDS),
                            equalTo(now.minus(Duration.ofMinutes(1)).truncatedTo(ChronoUnit.SECONDS))),
                    () -> assertThat(writtenPoints.get(writtenPoints.firstKey()), equalTo(1.0))//
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
            final SortedMap<Instant, Double> writtenPoints = localstackCloudWatchBackdoor.readMetrics("USERS",
                    expectedDimensions());
            assertThat(writtenPoints.size(), equalTo(3));
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
                    final CloudWatchAdapter adapter = new CloudWatchAdapter(schemaOverride,
                            LOCAL_STACK_CONTAINER.getEndpointOverride(CLOUDWATCH));
                    adapter.handleRequest(event, mock(Context.class));
                });
    }
}