package com.exasol.cloudwatch.exasolmetrics;

import static com.exasol.cloudwatch.TestConstants.EXASOL_DOCKER_DB_VERSION;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.itsallcode.io.Capturable;
import org.itsallcode.junit.sysextensions.SystemOutGuard;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.exasol.containers.ExasolContainer;

@Testcontainers
@ExtendWith(SystemOutGuard.class)
@SuppressWarnings("try") // Auto-closeable resource mockTable is never referenced in body of try statement
class ExasolEventMetricsReaderIT {
    @Container
    @SuppressWarnings("resource") // Will be closed by @Testcontainers annotation
    private static final ExasolContainer<? extends ExasolContainer<?>> EXASOL = new ExasolContainer<>(
            EXASOL_DOCKER_DB_VERSION).withReuse(true);
    private static final Instant NOW = Instant.parse("2007-12-03T10:15:59.00Z");
    private static final String CLUSTER1 = "cluster 1";
    private static final String CLUSTER2 = "cluster 2";
    private Connection exasolConnection;

    @BeforeEach
    void beforeEach() {
        this.exasolConnection = EXASOL.createConnection();
    }

    @AfterEach
    void afterEach() throws SQLException {
        this.exasolConnection.close();
    }

    @Test
    void readingEmptyEventListReturnsNoMetrics() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW.minusSeconds(30), CLUSTER1, "BACKUP_START");
            assertThat(runReader(NOW), hasSize(0));
        }
    }

    @Test
    void readingSingleEventListReturnsMetric() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW.minusSeconds(30), CLUSTER1, "BACKUP_START");
            mockTable.insert(NOW.minusSeconds(29), CLUSTER1, "BACKUP_END");
            mockTable.insert(NOW.minusSeconds(28), CLUSTER1, "OTHER_EVENT");
            final List<ExasolMetricDatum> metrics = runReader(NOW, "EVENT_BACKUP_START");
            assertAll(() -> assertThat(metrics, hasSize(1)),
                    () -> assertDatum(metrics.get(0), NOW.minusSeconds(30), CLUSTER1, ExasolEvent.BACKUP_START));
        }
    }

    @Test
    void doesNotReturnOlderEvents() throws SQLException {
        assertEventCount(NOW, Instant.parse("2007-12-03T10:14:59.999Z"), 0);
    }

    @Test
    void doesNotReturnNewerEvents() throws SQLException {
        assertEventCount(NOW, Instant.parse("2007-12-03T10:16:00.000Z"), 0);
    }

    @Test
    void findsEventJustAfterBegin() throws SQLException {
        assertEventCount(NOW, Instant.parse("2007-12-03T10:15:59.999Z"), 1);
    }

    @Test
    void findsEventJustBeforeEnd() throws SQLException {
        assertEventCount(NOW, Instant.parse("2007-12-03T10:15:00.000Z"), 1);
    }

    private void assertEventCount(final Instant currentTimestamp, final Instant eventTimestamp,
            final int expectedEventCount) throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(eventTimestamp, CLUSTER1, "BACKUP_START");
            final List<ExasolMetricDatum> metrics = runReader(currentTimestamp, "EVENT_BACKUP_START");
            assertThat(metrics, hasSize(expectedEventCount));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "BACKUP_START", "BACKUP_END", "BACKUP_ABORTED" })
    void findsEventType(final String eventType) throws SQLException {
        assertEventCount(eventType, allMetricsNames(), 1);
    }

    @ParameterizedTest
    @ValueSource(strings = { "backup_start", " BACKUP_END", "BACKUP_ABORTED ", "EVENT_BACKUP_ABORTED" })
    void ignoresNotMatchingEventType(final String eventType) throws SQLException {
        assertEventCount(eventType, allMetricsNames(), 0);
    }

    private List<String> allMetricsNames() {
        return Arrays.stream(ExasolEvent.values()).map(ExasolEvent::getMetricsName).collect(toList());
    }

    @Test
    void findsOnlyWantedEventTypes() throws SQLException {
        assertEventCount("BACKUP_START", List.of("EVENT_BACKUP_ABORTED"), 0);
    }

    @Test
    void returnsMultipleEvents() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW.minusSeconds(30), CLUSTER1, "BACKUP_START");
            mockTable.insert(NOW.minusSeconds(20), CLUSTER2, "BACKUP_ABORTED");
            mockTable.insert(NOW.minusSeconds(15), CLUSTER1, "BACKUP_END");
            final List<ExasolMetricDatum> metrics = runReader(NOW, allMetricsNames());
            assertThat(metrics, hasSize(3));
            assertDatum(metrics.get(0), NOW.minusSeconds(30), CLUSTER1, ExasolEvent.BACKUP_START);
            assertDatum(metrics.get(1), NOW.minusSeconds(20), CLUSTER2, ExasolEvent.BACKUP_ABORTED);
            assertDatum(metrics.get(2), NOW.minusSeconds(15), CLUSTER1, ExasolEvent.BACKUP_END);
        }
    }

    private void assertEventCount(final String eventType, final List<String> wantedTypes, final int expectedEventCount)
            throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW, CLUSTER1, eventType);
            final List<ExasolMetricDatum> metrics = runReader(NOW, wantedTypes.toArray(new String[0]));
            assertThat(metrics, hasSize(expectedEventCount));
        }
    }

    private void assertDatum(final ExasolMetricDatum datum, final Instant timestamp, final String clusterName,
            final ExasolEvent event) {
        assertAll( //
                () -> assertThat("cluster name", datum.getClusterName(), equalTo(clusterName)),
                () -> assertThat("metric name", datum.getMetricName(), equalTo(event.getMetricsName())),
                () -> assertThat("timestamp", datum.getTimestamp(), equalTo(timestamp)),
                () -> assertThat("unit", datum.getUnit(), equalTo(ExasolUnit.COUNT)),
                () -> assertThat("value", datum.getValue(), equalTo(1.0)));
    }

    @Test
    void readingEmptyTableReturnsNoMetrics() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            final Instant timestamp = Instant.ofEpochSecond(0);
            assertThat(runReader(timestamp, "EVENT_BACKUP_START", "EVENT_BACKUP_END", "EVENT_BACKUP_ABORTED"),
                    hasSize(0));
        }
    }

    @Test
    void logsWarningForSkippedPointsDueToTimeshift(final Capturable stdOutStream) throws SQLException {
        final Instant minuteToQuery = Instant.parse("2020-10-25T01:10:00Z");
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(Instant.parse("2020-10-25T01:10:00Z"), CLUSTER1, "BACKUP_START");
            stdOutStream.capture();
            final List<ExasolMetricDatum> result = runReader(minuteToQuery, allMetricsNames());
            assertAll( //
                    () -> assertThat(result, empty()), //
                    () -> assertThat(stdOutStream.getCapturedData(), containsString(
                            "WARN  ExasolEventMetricsReader - W-CWA-21: Skipping points due to timeshift")) //
            );
        }
    }

    @Test
    void failsWithSqlExceptionWhenTableIsMissing() {
        final IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> runReader(NOW, "EVENT_BACKUP_START"));
        assertThat(exception.getMessage(), startsWith("F-CWA-22: Failed to execute query"));
        assertThat(exception.getCause().getMessage().replace("\"", ""),
                startsWith("object MOCK_SCHEMA.EXA_SYSTEM_EVENTS not found"));
    }

    private List<ExasolMetricDatum> runReader(final Instant someWhen, final String... events) {
        return runReader(someWhen, asList(events));
    }

    private List<ExasolMetricDatum> runReader(final Instant someWhen, final List<String> events) {
        final ExasolMetricReader reader = new ExasolEventMetricsReaderFactory().getReader(EXASOL.createConnection(),
                ExaSystemEventsMockTable.MOCK_SCHEMA);
        return reader.readMetrics(events, someWhen);
    }
}
