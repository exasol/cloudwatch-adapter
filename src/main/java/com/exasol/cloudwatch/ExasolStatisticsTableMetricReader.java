package com.exasol.cloudwatch;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exasol.errorreporting.ExaError;

/**
 * This class reads the value of {@link ExasolStatisticsTableMetric}s from the Exasol database.
 */
public class ExasolStatisticsTableMetricReader {
    private static final TimeZone UTC_ZONE = TimeZone.getTimeZone("UTC");
    private static final Logger LOGGER = LoggerFactory.getLogger(ExasolStatisticsTableMetricReader.class);
    private final Connection connection;
    private final String schemaOverride;
    private final Calendar utcCalendar;

    /**
     * Create a new instance of {@link ExasolStatisticsTableMetricReader}.
     * 
     * @param connection     connection to the exasol database
     * @param schemaOverride if null EXA_STATISITCS is used. This parameter allows you to test this connector with a
     *                       predefined SCHEMA instead of the unmodifiable live statistics.
     */
    public ExasolStatisticsTableMetricReader(final Connection connection, final String schemaOverride) {
        this.connection = connection;
        this.schemaOverride = schemaOverride;
        this.utcCalendar = Calendar.getInstance(UTC_ZONE);
        setSessionTimezoneToUtc();
    }

    private void setSessionTimezoneToUtc() {
        try (final Statement statement = this.connection.createStatement()) {
            statement.executeUpdate("ALTER SESSION SET TIME_ZONE = 'UTC'");
        } catch (final SQLException exception) {
            throw new IllegalStateException(ExaError.messageBuilder("F-CWA-13")
                    .message("Failed to change the Exasol session time zone to UTC.").ticketMitigation().toString(),
                    exception);
        }
    }

    /**
     * Read the {@link SystemTableDataPoint} for given metrics and a given minute.
     * 
     * @param metrics  list of metrics to fetch the data for.
     * @param ofMinute minute to read the data for
     * @return found
     */
    // [impl->dsn~report-minute-before-event~1]
    public List<SystemTableDataPoint> readMetrics(final List<ExasolStatisticsTableMetric> metrics,
            final Instant ofMinute) {
        final Instant start = ofMinute.truncatedTo(ChronoUnit.MINUTES);
        final Instant end = start.plus(Duration.ofMinutes(1));
        assertIntervalIsInThePastForTheDatabaseTime(end);

        final Stream<ExasolStatisticsTable> requestedTables = metrics.stream()
                .map(ExasolStatisticsTableMetric::getTable).distinct();
        return requestedTables.flatMap(table -> loadMetricsForTable(metrics, start, end, table).stream())
                .collect(Collectors.toList());
    }

    private void assertIntervalIsInThePastForTheDatabaseTime(final Instant end) {
        final Instant databaseTime = getDatabaseTime();
        if (end.isAfter(databaseTime)) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-CWA-11").message(
                    "The current date of the database ({{db date}}) is before the end of the interval ({{end}}) of this report.")
                    .mitigation("Check your clock Exasol databases clock synchronization.")
                    .parameter("db date", databaseTime).parameter("end", end).toString());
        }
    }

    private Instant getDatabaseTime() {
        try (final Statement statement = this.connection.createStatement();
                final ResultSet resultSet = statement.executeQuery("SELECT NOW()")) {
            resultSet.next();
            return resultSet.getTimestamp(1, this.utcCalendar).toInstant();
        } catch (final SQLException exception) {
            throw new IllegalStateException(ExaError.messageBuilder("F-CWA-10")
                    .message("Failed to get current time of exasol database.").ticketMitigation().toString(),
                    exception);
        }
    }

    private List<SystemTableDataPoint> loadMetricsForTable(final List<ExasolStatisticsTableMetric> metrics,
            final Instant start, final Instant end, final ExasolStatisticsTable statisticsTable) {
        final List<ExasolStatisticsTableMetric> currentTablesMetrics = metrics.stream()
                .filter(metric -> metric.getTable().equals(statisticsTable)).collect(Collectors.toList());
        final String query = buildMetricsQuery(statisticsTable);
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setTimestamp(1, Timestamp.from(start), this.utcCalendar);
            statement.setTimestamp(2, Timestamp.from(end), this.utcCalendar);
            return executeSystemTableQuery(currentTablesMetrics, statement);
        } catch (final SQLException exception) {
            if (exception.getMessage().contains("ambigous timestamp")) {
                LOGGER.warn(ExaError.messageBuilder("W-CWA-12").message("Skipping points due to timeshift. ").message(
                        "Since the Exasol database stores the logs with dates in the DBTIMEZONE there are ambiguous logs during the timeshift.")
                        .mitigation("The only think you can do is to change your DBTIMEZONE to UTC.").toString());
                return List.of();
            } else {
                throw wrapSqlException(query, exception);
            }
        }
    }

    private IllegalStateException wrapSqlException(final String query, final SQLException exception) {
        return new IllegalStateException(
                ExaError.messageBuilder("F-CWA-1").message("Failed to execute query ({{query}}) on system table.")
                        .parameter("query", query).ticketMitigation().toString(),
                exception);
    }

    private String buildMetricsQuery(final ExasolStatisticsTable statisticsTable) {
        final String schema = this.schemaOverride == null ? "EXA_STATISTICS" : this.schemaOverride;
        final String schemaInjectionFree = schema.replace("\"", "");
        return "SELECT "
                + "CONVERT_TZ(MEASURE_TIME, DBTIMEZONE, 'UTC', 'INVALID REJECT AMBIGUOUS REJECT') as UTC_MEASURE_TIME, t.* "
                + "FROM \"" + schemaInjectionFree + "\".\"" + statisticsTable + "\"" + " t "
                + "WHERE MEASURE_TIME >= CONVERT_TZ(?, 'UTC', DBTIMEZONE, 'INVALID REJECT AMBIGUOUS REJECT') "
                + "AND MEASURE_TIME < CONVERT_TZ(?, 'UTC', DBTIMEZONE, 'INVALID REJECT AMBIGUOUS REJECT');";
    }

    private List<SystemTableDataPoint> executeSystemTableQuery(
            final List<ExasolStatisticsTableMetric> currentTablesMetrics, final PreparedStatement statement)
            throws SQLException {
        try (final ResultSet resultSet = statement.executeQuery()) {
            final List<SystemTableDataPoint> result = new ArrayList<>();
            while (resultSet.next()) {
                for (final ExasolStatisticsTableMetric metric : currentTablesMetrics) {
                    result.add(new SystemTableDataPoint(metric,
                            resultSet.getTimestamp("UTC_MEASURE_TIME", this.utcCalendar).toInstant(),
                            resultSet.getDouble(metric.name()), resultSet.getString("CLUSTER_NAME")));
                }
            }
            return result;
        }
    }
}
