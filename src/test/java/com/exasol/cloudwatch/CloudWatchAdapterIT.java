package com.exasol.cloudwatch;

import static com.exasol.cloudwatch.ExasolToCloudwatchMetricDatumConverter.CLUSTER_NAME_DIMENSION_KEY;
import static com.exasol.cloudwatch.ExasolToCloudwatchMetricDatumConverter.DEPLOYMENT_DIMENSION_KEY;
import static com.exasol.cloudwatch.TestConstants.EXASOL_DOCKER_DB_VERSION;
import static com.exasol.cloudwatch.TestConstants.LOCAL_STACK_IMAGE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.CLOUDWATCH;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SECRETSMANAGER;

import java.io.IOException;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.exasol.cloudwatch.configuration.MockEnvironmentVariableProvider;
import com.exasol.containers.ExasolContainer;

import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.*;

@Testcontainers
@SuppressWarnings("try") // Auto-closeable resource statisticsTable is never referenced in body of try statement
// [itest->dsn~env-var-for-metrics-selection~1]
class CloudWatchAdapterIT {
    @Container
    @SuppressWarnings("resource") // Will be closed by @Testcontainers
    private static final ExasolContainer<? extends ExasolContainer<?>> EXASOL = new ExasolContainer<>(
            EXASOL_DOCKER_DB_VERSION).withReuse(true);
    @Container
    @SuppressWarnings("resource") // Will be closed by @Testcontainers
    private static final LocalStackContainer LOCAL_STACK_CONTAINER = new LocalstackContainerWithReuse(
            DockerImageName.parse(LOCAL_STACK_IMAGE)).withServices(CLOUDWATCH, SECRETSMANAGER);
    private static final Logger LOGGER = LoggerFactory.getLogger(CloudWatchAdapterIT.class);
    private static Connection connection;
    private static CloudWatchClient cloudWatch;
    private static LocalStackTestInterface localStackTestInterface;
    private static LocalstackCloudWatchRaw localstackCloudWatchRaw;
    private static String secretArn;
    private String uniqueDeploymentName;

    @BeforeAll
    static void beforeAll() throws SQLException, IOException {
        connection = EXASOL.createConnection();
        localStackTestInterface = new LocalStackTestInterface(LOCAL_STACK_CONTAINER);
        cloudWatch = localStackTestInterface.getCloudWatchClient();
        localstackCloudWatchRaw = new LocalstackCloudWatchRaw(LOCAL_STACK_CONTAINER);
        secretArn = createCredentials(getCertificateFingerprint());
    }

    private static String createCredentials(final String certificateFingerprint) throws IOException {
        return localStackTestInterface.putExasolCredentials(EXASOL.getHost(), EXASOL.getFirstMappedPort().toString(),
                EXASOL.getUsername(), EXASOL.getPassword(), certificateFingerprint);
    }

    private static String getCertificateFingerprint() {
        return EXASOL.getTlsCertificateFingerprint().orElse(null);
    }

    @AfterAll
    static void afterAll() throws SQLException {
        localStackTestInterface.deleteSecret(secretArn);
        connection.close();
        cloudWatch.close();
    }

    @BeforeEach
    void beforeEach() {
        this.uniqueDeploymentName = "TEST-" + new Date().getTime() + "-" + ((int) (Math.random() * 1000));
        LOGGER.info("current deployment name: {}", this.uniqueDeploymentName);
    }

    @CsvSource(nullValues = { "NULL" }, value = {
            "NULL, .*TLS connection to host (.*) failed: PKIX path building failed.*",
            "'', .*TLS connection to host (.*) failed: PKIX path building failed.*",
            "'  ', .*TLS connection to host (.*) failed: PKIX path building failed.*",
            "'invalid-fingerprint', .*Fingerprint did not match. The fingerprint provided: INVALID-FINGERPRINT.*" })
    @ParameterizedTest
    void testConnectionWithWrongCertificateFingerprintFails(final String fingerprint,
            final String expectedErrorMessageRegexp) throws Exception {
        final String secretArnWithoutFingerprint = createCredentials(fingerprint);
        try (final ExaStatisticsTableMock statisticsTable = new ExaStatisticsTableMock(connection)) {
            final Instant now = Instant.now();
            final IllegalStateException exception = assertThrows(IllegalStateException.class,
                    () -> runAdapter(secretArnWithoutFingerprint, "QUERIES", now));
            assertAll(
                    () -> assertThat(exception.getMessage(),
                            equalTo("E-CWA-2: Failed to connect to the Exasol database.")),
                    () -> assertThat(exception.getCause().getMessage(), matchesRegex(expectedErrorMessageRegexp)));
        } finally {
            localStackTestInterface.deleteSecret(secretArnWithoutFingerprint);
        }
    }

    @Test
    void testMetricsAreCreated() throws Exception {
        try (final ExaStatisticsTableMock statisticsTable = new ExaStatisticsTableMock(connection)) {
            final Instant now = Instant.now();
            statisticsTable.addRows(Stream.of(new ExaStatisticsTableMock.Row(
                    now.minus(Duration.ofMinutes(1)).minus(Duration.ofSeconds(1)), "MAIN", 10, 1)));
            runAdapter("QUERIES", now);
            final List<Metric> metrics = listCurrentDeploymentsMetrics();
            assertThat(metrics.size(), equalTo(1));
            final Metric firstMetric = metrics.get(0);
            final List<Dimension> dimensions = firstMetric.dimensions();
            assertAll(() -> assertThat(firstMetric.metricName(), equalTo("QUERIES")),
                    () -> assertThat(firstMetric.namespace(), equalTo("Exasol")), () -> assertThat(dimensions,
                            hasItem(Dimension.builder().name("Cluster Name").value("MAIN").build()))//
            );
        }
    }

    @Test
    void testUnknownMetric() throws Exception {
        try (final ExaStatisticsTableMock statisticsTable = new ExaStatisticsTableMock(connection)) {
            final Instant now = Instant.now();
            final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> runAdapter("UNKNOWN", now));
            assertThat(exception.getMessage(), startsWith("E-CWA-31"));
        }
    }

    @Test
    void testMetricPointIsPut() throws Exception {
        try (final ExaStatisticsTableMock statisticsTable = new ExaStatisticsTableMock(connection)) {
            final Instant now = Instant.now();
            mockLogs(statisticsTable, now, 5, 0);
            runAdapter("USERS", now);
            final SortedMap<Instant, Double> writtenPoints = localstackCloudWatchRaw.readMetrics("USERS",
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
            runAdapter("USERS", now.minus(Duration.ofMinutes(2)));
            runAdapter("USERS", now.minus(Duration.ofMinutes(1)));
            runAdapter("USERS", now);
            final SortedMap<Instant, Double> writtenPoints = localstackCloudWatchRaw.readMetrics("USERS",
                    expectedDimensions());
            assertThat(writtenPoints.size(), equalTo(3));
        }
    }

    private void mockLogs(final ExaStatisticsTableMock statisticsTable, final Instant now,
            final int startInMinutesBeforeNow, final int endInMinutesBeforeNow) {
        statisticsTable
                .addRows(
                        IntStream.range(0, startInMinutesBeforeNow - endInMinutesBeforeNow)
                                .mapToObj(counter -> new ExaStatisticsTableMock.Row(
                                        now.minus(Duration.ofSeconds((endInMinutesBeforeNow + counter) * 60L)), "MAIN",
                                        1, 1)));
    }

    private Dimension[] expectedDimensions() {
        return new Dimension[] {
                Dimension.builder().name(DEPLOYMENT_DIMENSION_KEY).value(this.uniqueDeploymentName).build(),
                Dimension.builder().name(CLUSTER_NAME_DIMENSION_KEY).value("MAIN").build() };
    }

    private List<Metric> listCurrentDeploymentsMetrics() {
        final ListMetricsRequest listRequest = ListMetricsRequest.builder().dimensions(
                DimensionFilter.builder().name(DEPLOYMENT_DIMENSION_KEY).value(this.uniqueDeploymentName).build())
                .build();
        return cloudWatch.listMetrics(listRequest).metrics();
    }

    private void runAdapter(final String overrideSecretArn, final String metrics, final Instant forMinute) {
        final MockEnvironmentVariableProvider mockEnvironment = new MockEnvironmentVariableProvider();
        mockEnvironment.put("EXASOL_CONNECTION_SECRET_ARN", overrideSecretArn);
        mockEnvironment.put("EXASOL_DEPLOYMENT_NAME", this.uniqueDeploymentName);
        mockEnvironment.put("METRICS", metrics);
        final ScheduledEvent event = new ScheduledEvent();
        event.setTime(new DateTime(forMinute.toEpochMilli()));
        final CloudWatchAdapter adapter = new CloudWatchAdapter(ExaStatisticsTableMock.SCHEMA, localStackTestInterface,
                mockEnvironment);
        adapter.handleRequest(event, mock(Context.class));
    }

    private void runAdapter(final String metrics, final Instant forMinute) {
        runAdapter(CloudWatchAdapterIT.secretArn, metrics, forMinute);
    }
}
