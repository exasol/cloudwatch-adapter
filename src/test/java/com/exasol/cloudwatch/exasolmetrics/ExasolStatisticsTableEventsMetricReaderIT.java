package com.exasol.cloudwatch.exasolmetrics;

import static com.exasol.cloudwatch.TestConstants.EXASOL_DOCKER_DB_VERSION;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import com.exasol.containers.ExasolContainer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class ExasolStatisticsTableEventsMetricReaderIT {
    @Container
    private static final ExasolContainer<? extends ExasolContainer<?>> EXASOL = new ExasolContainer<>(
            EXASOL_DOCKER_DB_VERSION).withReuse(true);

    private static Connection exasolConnection;

    private static final Instant NOW = Instant.parse("2007-12-03T10:15:30.00Z");

    @BeforeAll
    static void beforeAll() throws SQLException {
        exasolConnection = EXASOL.createConnection();
    }

    @AfterAll
    static void afterAll() throws SQLException {
        exasolConnection.close();
    }

    @Test
    void testNoMetricsRequested() throws Exception {
        try (final ExaSystemEventsMockTable mockTable = new ExaSystemEventsMockTable(exasolConnection)) {
            final Instant someWhen = Instant.ofEpochSecond(0);
            mockTable.insert(someWhen, 10, 2, "MAIN");
            mockTable.insert(someWhen.plus(Duration.ofHours(1)), 100, 4, "MAIN");
            mockTable.insert(someWhen.minus(Duration.ofHours(1)), 5, 1, "MAIN");
            final List<ExasolMetricDatum> result = runReader(someWhen);
            assertThat(result, hasSize(0));
        }
    }

    @Test
    void testTwoMetricsRequested() throws Exception {
        try (final ExaSystemEventsMockTable mockTable = new ExaSystemEventsMockTable(exasolConnection)) {
            final Instant someWhen = Instant.ofEpochSecond(0);
            mockTable.insert(someWhen, 10, 2, "MAIN");
            mockTable.insert(someWhen.plus(Duration.ofHours(1)), 100, 4, "MAIN");
            mockTable.insert(someWhen.minus(Duration.ofHours(1)), 5, 1, "MAIN");
            final List<ExasolMetricDatum> result = runReader(someWhen, "NODES", "DB_RAM_SIZE");
            assertAll( //
                    () -> assertThat(result, hasSize(2)), //
                    () -> assertThat(result.get(0).getMetricName(), equalTo("NODES")), //
                    () -> assertThat(result.get(0).getClusterName(), equalTo("MAIN")), //
                    () -> assertThat(result.get(0).getUnit(), equalTo(ExasolUnit.COUNT)), //
                    () -> assertThat(result.get(0).getValue(), equalTo(4.0)), //
                    () -> assertThat(result.get(0).getTimestamp(), equalTo(NOW)), //
                    () -> assertThat(result.get(1).getMetricName(), equalTo("DB_RAM_SIZE")), //
                    () -> assertThat(result.get(1).getClusterName(), equalTo("MAIN")), //
                    () -> assertThat(result.get(1).getUnit(), equalTo(ExasolUnit.GIBIBYTES)), //
                    () -> assertThat(result.get(1).getValue(), equalTo(100.0)), //
                    () -> assertThat(result.get(1).getTimestamp(), equalTo(NOW)));
        }
    }

    @Test
    void testLatestIsReported() throws Exception {
        try (final ExaSystemEventsMockTable mockTable = new ExaSystemEventsMockTable(exasolConnection)) {
            final Instant someWhen = Instant.ofEpochSecond(0);
            mockTable.insert(someWhen, 10, 2, "MAIN");
            mockTable.insert(someWhen.plus(Duration.ofHours(1)), 100, 4, "MAIN");
            mockTable.insert(someWhen.minus(Duration.ofHours(1)), 5, 1, "MAIN");
            final List<ExasolMetricDatum> result = runReader(someWhen, "NODES");
            assertAll( //
                    () -> assertThat(result, hasSize(1)), //
                    () -> assertThat(result.size(), equalTo(1)), //
                    () -> assertThat(result.get(0).getValue(), equalTo(4.0))//
            );
        }
    }

    @Test
    void testReportedOnlyEveryFourMinutes() throws Exception {
        try (final ExaSystemEventsMockTable mockTable = new ExaSystemEventsMockTable(exasolConnection)) {
            final Instant someWhen = Instant.ofEpochSecond(0);
            mockTable.insert(someWhen, 10, 2, "MAIN");
            int resultCounter = 0;
            for (int minutes = 0; minutes < 8; minutes++) {
                final List<ExasolMetricDatum> result = runReader(someWhen.plus(Duration.ofMinutes(minutes)), "NODES");
                resultCounter += result.size();
            }
            assertThat(
                    "The adapter reports the NODES metric twice in 8 minutes. (The metric should get reported once every 4 mins)",
                    resultCounter, equalTo(2));
        }
    }

    @Test
    void testMultipleClusters() throws Exception {
        try (final ExaSystemEventsMockTable mockTable = new ExaSystemEventsMockTable(exasolConnection)) {
            final Instant someWhen = Instant.ofEpochSecond(0);
            mockTable.insert(someWhen, 10, 2, "MAIN");
            mockTable.insert(someWhen, 100, 4, "OTHER");
            final List<ExasolMetricDatum> result = runReader(someWhen, "NODES");
            assertThat(result, hasSize(2));
            final List<String> clusterNames = result.stream().map(ExasolMetricDatum::getClusterName)
                    .collect(Collectors.toList());
            assertThat(clusterNames, containsInAnyOrder("MAIN", "OTHER"));
        }
    }

    private List<ExasolMetricDatum> runReader(final Instant someWhen, final String... metrics) {
        final ExasolStatisticsTableEventsMetricReader reader = new ExasolStatisticsTableEventsMetricReader(
                exasolConnection, ExaSystemEventsMockTable.MOCK_SCHEMA, Clock.fixed(NOW, ZoneId.of("UTC")));
        return reader.readMetrics(asList(metrics), someWhen.plus(Duration.ofHours(100)));
    }
}