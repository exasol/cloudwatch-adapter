package com.exasol.cloudwatch.exasolmetrics;

import static com.exasol.cloudwatch.TestConstants.EXASOL_DOCKER_DB_VERSION;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.exasol.containers.ExasolContainer;

@Testcontainers
class ExasolStatisticsTableEventsMetricReaderIT {
    @Container
    private static final ExasolContainer<? extends ExasolContainer<?>> EXASOL = new ExasolContainer<>(
            EXASOL_DOCKER_DB_VERSION).withReuse(true);
    private static final String MOCK_SCHEMA = "MOCK_SCHEMA";
    private static final String EXA_SYSTEM_EVENTS = "EXA_SYSTEM_EVENTS";
    private static Connection exasolConnection;
    private static Statement statement;

    @BeforeAll
    static void beforeAll() throws SQLException {
        exasolConnection = EXASOL.createConnection();
        statement = exasolConnection.createStatement();
    }

    @AfterAll
    static void afterAll() throws SQLException {
        statement.close();
        exasolConnection.close();
    }

    @Test
    void testLatestIsReported() throws Exception {
        try (final ExaSystemEventsMockTable mockTable = new ExaSystemEventsMockTable()) {
            final Instant someWhen = Instant.ofEpochSecond(0);
            mockTable.insert(someWhen, 10, 2, "MAIN");
            mockTable.insert(someWhen.plus(Duration.ofHours(1)), 100, 4, "MAIN");
            mockTable.insert(someWhen.minus(Duration.ofHours(1)), 5, 1, "MAIN");
            final List<ExasolMetricDatum> result = runReader(someWhen);
            final ExasolMetricDatum first = result.get(0);
            assertAll(//
                    () -> assertThat(result.size(), equalTo(1)), //
                    () -> assertThat(first.getValue(), equalTo(4.0))//
            );
        }
    }

    @Test
    void testReportedOnlyEveryFourMinutes() throws Exception {
        try (final ExaSystemEventsMockTable mockTable = new ExaSystemEventsMockTable()) {
            final Instant someWhen = Instant.ofEpochSecond(0);
            mockTable.insert(someWhen, 10, 2, "MAIN");
            int resultCounter = 0;
            for (int minutes = 0; minutes < 8; minutes++) {
                final List<ExasolMetricDatum> result = runReader(someWhen.plus(Duration.ofMinutes(minutes)));
                resultCounter += result.size();
            }
            assertThat(
                    "The adapter reports the NODES metric twice in 8 minutes. (The metric should get reported once every 4 mins)",
                    resultCounter, equalTo(2));
        }
    }

    @Test
    void testMultipleClusters() throws Exception {
        try (final ExaSystemEventsMockTable mockTable = new ExaSystemEventsMockTable()) {
            final Instant someWhen = Instant.ofEpochSecond(0);
            mockTable.insert(someWhen, 10, 2, "MAIN");
            mockTable.insert(someWhen, 100, 4, "OTHER");
            final List<ExasolMetricDatum> result = runReader(someWhen);
            final List<String> clusterNames = result.stream().map(ExasolMetricDatum::getClusterName)
                    .collect(Collectors.toList());
            assertThat(clusterNames, containsInAnyOrder("MAIN", "OTHER"));
        }
    }

    private List<ExasolMetricDatum> runReader(final Instant someWhen) {
        final ExasolStatisticsTableEventsMetricReader reader = new ExasolStatisticsTableEventsMetricReader(
                exasolConnection, MOCK_SCHEMA);
        return reader.readMetrics(List.of("NODES"), someWhen.plus(Duration.ofHours(100)));
    }

    private static class ExaSystemEventsMockTable implements AutoCloseable {
        private final Calendar utcCalendar;

        public ExaSystemEventsMockTable() throws SQLException {
            statement.executeUpdate("CREATE SCHEMA " + MOCK_SCHEMA);
            statement.executeUpdate("CREATE TABLE " + MOCK_SCHEMA + "." + EXA_SYSTEM_EVENTS + " LIKE EXA_STATISTICS."
                    + EXA_SYSTEM_EVENTS + ";");
            this.utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        }

        public void insert(final Instant measureTime, final double dbRamSize, final int nodes, final String clusterName)
                throws SQLException {
            final PreparedStatement insertStatement = exasolConnection.prepareStatement(
                    "INSERT INTO " + MOCK_SCHEMA + "." + EXA_SYSTEM_EVENTS + " VALUES(?,?,'', '', ?, ?, '')");
            insertStatement.setString(1, clusterName);
            insertStatement.setTimestamp(2, Timestamp.from(measureTime), this.utcCalendar);
            insertStatement.setInt(3, nodes);
            insertStatement.setDouble(4, dbRamSize);
            insertStatement.executeUpdate();
        }

        @Override
        public void close() throws Exception {
            statement.executeUpdate("DROP TABLE " + MOCK_SCHEMA + "." + EXA_SYSTEM_EVENTS);
            statement.executeUpdate("DROP SCHEMA " + MOCK_SCHEMA);
        }
    }
}