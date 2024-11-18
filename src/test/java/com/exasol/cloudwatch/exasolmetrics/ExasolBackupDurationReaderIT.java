package com.exasol.cloudwatch.exasolmetrics;

import static com.exasol.cloudwatch.TestConstants.EXASOL_DOCKER_DB_VERSION;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

import org.itsallcode.io.Capturable;
import org.itsallcode.junit.sysextensions.SystemOutGuard;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opentest4j.MultipleFailuresError;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.exasol.containers.ExasolContainer;

@Testcontainers
@ExtendWith(SystemOutGuard.class)
@SuppressWarnings("try") // Auto-closeable resource mockTable is never referenced in body of try statement
class ExasolBackupDurationReaderIT {
    @Container
    @SuppressWarnings("resource") // Will be closed by @Testcontainers
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
    void readingEmptyTableReturnsNoMetrics() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            assertThat(runReader(NOW), hasSize(0));
        }
    }

    @Test
    void noMetricSpecifiedReturnsNothing() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW.minusSeconds(20), CLUSTER1, "BACKUP_START");
            mockTable.insert(NOW.minusSeconds(10), CLUSTER1, "BACKUP_END");
            assertThat(runReaderWithoutEvent(NOW), hasSize(0));
        }
    }

    private List<ExasolMetricDatum> runReaderWithoutEvent(final Instant someWhen) {
        return runReader(someWhen, emptyList());
    }

    @Test
    void backupDurationFound() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW.minusSeconds(20), CLUSTER1, "BACKUP_START");
            mockTable.insert(NOW.minusSeconds(10), CLUSTER1, "BACKUP_END");
            assertBackupDuration(NOW, CLUSTER1, NOW.minusSeconds(10), 10);
        }
    }

    @Test
    void backupAbortedEventFound() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW.minusSeconds(20), CLUSTER1, "BACKUP_START");
            mockTable.insert(NOW.minusSeconds(10), CLUSTER1, "BACKUP_ABORTED");
            assertBackupDuration(NOW, CLUSTER1, NOW.minusSeconds(10), 10);
        }
    }

    @Test
    void unknownBackupEndEventIgnored() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW.minusSeconds(20), CLUSTER1, "BACKUP_START");
            mockTable.insert(NOW.minusSeconds(10), CLUSTER1, "BACKUP_unknown");
            assertBackupDuration(NOW, CLUSTER1, NOW.minusSeconds(10), 10);
        }
    }

    @Test
    void twoBackupStartEventIgnored() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW.minusSeconds(20), CLUSTER1, "BACKUP_START");
            mockTable.insert(NOW.minusSeconds(10), CLUSTER1, "BACKUP_START");
            assertNoBackupDuration(NOW);
        }
    }

    @Test
    void futureBackupEndIgnored() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW.minusSeconds(20), CLUSTER1, "BACKUP_START");
            mockTable.insert(NOW.plusSeconds(120), CLUSTER1, "BACKUP_END");
            assertNoBackupDuration(NOW);
        }
    }

    @Test
    void backupEndBeforeTimeLimitIgnored() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW.minusSeconds(120), CLUSTER1, "BACKUP_START");
            mockTable.insert(NOW.minusSeconds(60), CLUSTER1, "BACKUP_END");
            assertNoBackupDuration(NOW);
        }
    }

    @Test
    void backupEndAtTimeLimitFound() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW.minusSeconds(120), CLUSTER1, "BACKUP_START");
            mockTable.insert(NOW.minusSeconds(59), CLUSTER1, "BACKUP_END");
            assertBackupDuration(NOW, CLUSTER1, NOW.minusSeconds(59), 61);
        }
    }

    @Test
    void backupStartBeforeReportingTimeLimit() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW.minusSeconds(120), CLUSTER1, "BACKUP_START");
            mockTable.insert(NOW.minusSeconds(10), CLUSTER1, "BACKUP_END");
            assertBackupDuration(NOW, CLUSTER1, NOW.minusSeconds(10), 110);
        }
    }

    @Test
    void startEventMissingReturnsNoResult() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW.minusSeconds(10), CLUSTER1, "BACKUP_END");
            assertNoBackupDuration(NOW);
        }
    }

    @Test
    void endEventMissingReturnsNoResult() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW.minusSeconds(20), CLUSTER1, "BACKUP_START");
            assertNoBackupDuration(NOW);
        }
    }

    @Test
    void startAndEndEventInWrongOrderReturnsNoResult() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW.minusSeconds(10), CLUSTER1, "BACKUP_START");
            mockTable.insert(NOW.minusSeconds(20), CLUSTER1, "BACKUP_END");
            assertNoBackupDuration(NOW);
        }
    }

    @Test
    void backupDurationFoundForTwoClusters() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW.minusSeconds(20), CLUSTER1, "BACKUP_START");
            mockTable.insert(NOW.minusSeconds(10), CLUSTER1, "BACKUP_END");
            mockTable.insert(NOW.minusSeconds(40), CLUSTER2, "BACKUP_START");
            mockTable.insert(NOW.minusSeconds(15), CLUSTER2, "BACKUP_END");
            final List<ExasolMetricDatum> metrics = runReader(NOW);
            assertThat(metrics, hasSize(2));
            assertMetricDatum(metrics.get(0), CLUSTER1, NOW.minusSeconds(10), 10);
            assertMetricDatum(metrics.get(1), CLUSTER2, NOW.minusSeconds(15), 25);
        }
    }

    @Test
    void twoBackupsForSameCluster() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW.minusSeconds(20), CLUSTER1, "BACKUP_START");
            mockTable.insert(NOW.minusSeconds(10), CLUSTER1, "BACKUP_END");
            mockTable.insert(NOW.minusSeconds(40), CLUSTER2, "BACKUP_START");
            mockTable.insert(NOW.minusSeconds(35), CLUSTER2, "BACKUP_END");
            final List<ExasolMetricDatum> metrics = runReader(NOW);
            assertThat(metrics, hasSize(2));
            assertMetricDatum(metrics.get(0), CLUSTER1, NOW.minusSeconds(10), 10);
            assertMetricDatum(metrics.get(1), CLUSTER2, NOW.minusSeconds(35), 5);
        }
    }

    @Test
    void onlyFirstEndEventConsidered() throws SQLException {
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(NOW.minusSeconds(20), CLUSTER1, "BACKUP_START");
            mockTable.insert(NOW.minusSeconds(10), CLUSTER1, "BACKUP_END");
            mockTable.insert(NOW.minusSeconds(40), CLUSTER1, "BACKUP_START");
            mockTable.insert(NOW.minusSeconds(15), CLUSTER1, "BACKUP_END");
            final List<ExasolMetricDatum> metrics = runReader(NOW);
            assertThat(metrics, hasSize(1));
            assertMetricDatum(metrics.get(0), CLUSTER1, NOW.minusSeconds(15), 5);
        }
    }

    @Test
    void logsWarningForSkippedPointsDueToTimeshift(final Capturable stdOutStream) throws SQLException {
        final Instant minuteToQuery = Instant.parse("2020-10-25T01:10:00Z");
        try (final ExaSystemEventsMockTable mockTable = ExaSystemEventsMockTable.forDbTimeZone(this.exasolConnection)) {
            mockTable.insert(minuteToQuery.minusSeconds(5), CLUSTER1, "BACKUP_START");
            mockTable.insert(minuteToQuery, CLUSTER1, "BACKUP_END");
            stdOutStream.capture();
            final List<ExasolMetricDatum> result = runReader(minuteToQuery);
            assertAll( //
                    () -> assertThat(result, empty()), //
                    () -> assertThat(stdOutStream.getCapturedData(), containsString(
                            "WARN  ExasolBackupDurationReader - W-CWA-34: Skipping points due to timeshift")) //
            );
        }
    }

    @Test
    void failsWithSqlExceptionWhenTableIsMissing() {
        final IllegalStateException exception = assertThrows(IllegalStateException.class, () -> runReader(NOW));
        assertThat(exception.getMessage(), startsWith("F-CWA-35: Failed to execute query"));
        assertThat(exception.getCause().getMessage().replace("\"", ""),
                startsWith("object MOCK_SCHEMA.EXA_SYSTEM_EVENTS not found"));
    }

    private void assertNoBackupDuration(final Instant someWhen) {
        assertThat(runReader(someWhen), hasSize(0));
    }

    private void assertBackupDuration(final Instant someWhen, final String expectedCluster,
            final Instant expectedTimestamp, final double expectedDuration) {
        final List<ExasolMetricDatum> metrics = runReader(someWhen);
        assertThat(metrics, hasSize(1));
        assertMetricDatum(metrics.get(0), expectedCluster, expectedTimestamp, expectedDuration);
    }

    private void assertMetricDatum(final ExasolMetricDatum actualMetricDatum, final String expectedCluster,
            final Instant expectedTimestamp, final double expectedDuration) throws MultipleFailuresError {
        assertAll(() -> assertThat("cluster name", actualMetricDatum.getClusterName(), equalTo(expectedCluster)),
                () -> assertThat("metrics name", actualMetricDatum.getMetricName(), equalTo("BACKUP_DURATION")),
                () -> assertThat("timestamp", actualMetricDatum.getTimestamp(), equalTo(expectedTimestamp)),
                () -> assertThat("unit", actualMetricDatum.getUnit(), equalTo(ExasolUnit.SECONDS)),
                () -> assertThat("backup duration", actualMetricDatum.getValue(), closeTo(expectedDuration, 0.000001)));
    }

    private List<ExasolMetricDatum> runReader(final Instant someWhen) {
        return runReader(someWhen, List.of("BACKUP_DURATION"));
    }

    private List<ExasolMetricDatum> runReader(final Instant someWhen, final List<String> events) {
        final ExasolMetricReader reader = new ExasolBackupDurationReaderFactory().getReader(EXASOL.createConnection(),
                ExaSystemEventsMockTable.MOCK_SCHEMA);
        return reader.readMetrics(events, someWhen);
    }
}
