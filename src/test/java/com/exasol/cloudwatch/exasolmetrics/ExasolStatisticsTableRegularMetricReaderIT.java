package com.exasol.cloudwatch.exasolmetrics;

import static com.exasol.cloudwatch.TestConstants.EXASOL_DOCKER_DB_VERSION;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.itsallcode.io.Capturable;
import org.itsallcode.junit.sysextensions.SystemOutGuard;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.exasol.cloudwatch.ExaStatisticsTableMock;
import com.exasol.containers.ExasolContainer;

@Testcontainers
@ExtendWith(SystemOutGuard.class)
class ExasolStatisticsTableRegularMetricReaderIT {
    @Container
    private static final ExasolContainer<? extends ExasolContainer<?>> EXASOL = new ExasolContainer<>(
            EXASOL_DOCKER_DB_VERSION).withReuse(true);
    private static final String CLUSTER_NAME = "MAIN";
    private static final ExaStatisticsTableMock.Row ROW_IN_TIME_GAP_AT_FORWARD_TIME_SHIFT = new ExaStatisticsTableMock.Row(
            "2020-03-29 02:10:00", CLUSTER_NAME, 0, 0);
    private static final ExaStatisticsTableMock.Row ROW_AFTER_TIME_GAP_AT_FORWARD_TIME_SHIFT = new ExaStatisticsTableMock.Row(
            "2020-03-29 03:10:00", CLUSTER_NAME, 0, 0);
    private static final ExaStatisticsTableMock.Row ROW_WITH_BACKWARD_TIME_SHIFT = new ExaStatisticsTableMock.Row(
            "2020-10-25 02:10:00", CLUSTER_NAME, 0, 0);
    private static Connection connection;

    @BeforeAll
    static void beforeAll() {
        connection = EXASOL.createConnection();
    }

    @AfterAll
    static void afterAll() throws SQLException {
        connection.close();
    }

    private static Stream<Arguments> getAllMetrics() {
        return Arrays.stream(ExasolStatisticsTableRegularMetric.values()).map(Enum::toString).map(Arguments::of);
    }

    /**
     * This test checks that the {@link ExasolStatisticsTableRegularMetric} only reads the points of the configured
     * minute.
     *
     * @throws SQLException if something goes wrong
     */
    @Test
    // [utest->dsn~report-minute-before-event~1]
    void testWithMockTable() throws SQLException {
        final List<ExasolMetricDatum> result = runQueryForMinuteOnMockTable(Instant.ofEpochSecond(61));
        assertThat(result, containsInAnyOrder(
                new ExasolMetricDatum("USERS", ExasolUnit.COUNT, Instant.ofEpochSecond(60), 1, CLUSTER_NAME),
                new ExasolMetricDatum("QUERIES", ExasolUnit.COUNT, Instant.ofEpochSecond(60), 10, CLUSTER_NAME),
                new ExasolMetricDatum("USERS", ExasolUnit.COUNT, Instant.ofEpochSecond(60 + 59), 2, CLUSTER_NAME),
                new ExasolMetricDatum("QUERIES", ExasolUnit.COUNT, Instant.ofEpochSecond(60 + 59), 20, CLUSTER_NAME)));
    }

    @Test
    void testWithMockTableDuringBackwardTimeShift(final Capturable stdOutStream) throws SQLException {
        final Instant minuteToQuery = Instant.parse("2020-10-25T01:10:00Z");
        stdOutStream.capture();
        final List<ExasolMetricDatum> result = runQueryForMinuteOnMockTable(minuteToQuery);
        assertAll(//
                () -> assertThat(result, empty()),
                () -> assertThat(stdOutStream.getCapturedData(), containsString("W-CWA-12"))//
        );
    }

    @Test
    void testWithMockTableDuringForwardTimeShift() throws SQLException {
        final Instant minuteToQuery = Instant.parse("2020-03-29T01:10:00Z");
        final List<ExasolMetricDatum> result = runQueryForMinuteOnMockTable(minuteToQuery);
        assertThat(result,
                containsInAnyOrder(new ExasolMetricDatum("QUERIES", ExasolUnit.COUNT, minuteToQuery, 0, CLUSTER_NAME),
                        new ExasolMetricDatum("USERS", ExasolUnit.COUNT, minuteToQuery, 0, CLUSTER_NAME)));
    }

    @Test
    void testWithMockTableWithRecentEntry() throws SQLException {
        final Instant previousMinute = Instant.now().truncatedTo(ChronoUnit.SECONDS).minus(Duration.ofMinutes(1));
        final List<ExasolMetricDatum> result = runQueryForMinuteOnMockTableWithRowForSameMinute(previousMinute);
        assertThat(result,
                containsInAnyOrder(new ExasolMetricDatum("QUERIES", ExasolUnit.COUNT, previousMinute, 0, CLUSTER_NAME),
                        new ExasolMetricDatum("USERS", ExasolUnit.COUNT, previousMinute, 0, CLUSTER_NAME)));
    }

    @Test
    void testWithMockTableThatOnlyDataOlderThanAMinuteIsReported() {
        final Instant previousMinute = Instant.now();
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> runQueryForMinuteOnMockTableWithRowForSameMinute(previousMinute));
        assertThat(exception.getMessage(), containsString("E-CWA-11"));
    }

    private List<ExasolMetricDatum> runQueryForMinuteOnMockTableWithRowForSameMinute(final Instant minute)
            throws SQLException {
        try (final ExaStatisticsTableMock exaStatisticsTableMock = new ExaStatisticsTableMock(connection)) {
            exaStatisticsTableMock.addRows(Stream.of(new ExaStatisticsTableMock.Row(minute, CLUSTER_NAME, 0, 0)));
            final AbstractExasolStatisticsTableMetricReader exasolStatisticsTableMetricReader = new ExasolStatisticsTableRegularMetricReader(
                    connection, ExaStatisticsTableMock.SCHEMA);
            return exasolStatisticsTableMetricReader.readMetrics(List.of("USERS", "QUERIES"), minute);
        }
    }

    @ParameterizedTest
    @MethodSource("getAllMetrics")
    void testReadAllMetrics(final String metric) {
        final AbstractExasolStatisticsTableMetricReader exasolStatisticsTableMetricReader = new ExasolStatisticsTableRegularMetricReader(
                connection, null);
        assertDoesNotThrow(() -> exasolStatisticsTableMetricReader.readMetrics(List.of(metric),
                Instant.now().minus(Duration.ofMinutes(10))));
    }

    private List<ExasolMetricDatum> runQueryForMinuteOnMockTable(final Instant minuteToQuery) throws SQLException {
        try (final ExaStatisticsTableMock exaStatisticsTableMock = new ExaStatisticsTableMock(connection)) {
            exaStatisticsTableMock
                    .addRows(Stream.of(new ExaStatisticsTableMock.Row("1970-01-01 01:00:00", CLUSTER_NAME, 0, 0),
                            new ExaStatisticsTableMock.Row("1970-01-01 01:01:00", CLUSTER_NAME, 1, 10),
                            new ExaStatisticsTableMock.Row("1970-01-01 01:01:59", CLUSTER_NAME, 2, 20),
                            new ExaStatisticsTableMock.Row("1970-01-01 01:02:00", CLUSTER_NAME, 3, 30),
                            ROW_WITH_BACKWARD_TIME_SHIFT, ROW_IN_TIME_GAP_AT_FORWARD_TIME_SHIFT,
                            ROW_AFTER_TIME_GAP_AT_FORWARD_TIME_SHIFT));
            final AbstractExasolStatisticsTableMetricReader exasolStatisticsTableMetricReader = new ExasolStatisticsTableRegularMetricReader(
                    connection, ExaStatisticsTableMock.SCHEMA);
            return exasolStatisticsTableMetricReader.readMetrics(List.of("USERS", "QUERIES"), minuteToQuery);
        }
    }
}
