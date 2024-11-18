package com.exasol.cloudwatch.exasolmetrics;

import static java.util.Collections.emptyList;

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
 * This class reads the value of {@link ExasolStatisticsTableRegularMetric}s from the Exasol database.
 */
class ExasolStatisticsTableRegularMetricReader extends AbstractExasolStatisticsTableMetricReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExasolStatisticsTableRegularMetricReader.class);

    /**
     * Create a new instance of {@link ExasolStatisticsTableRegularMetricReader}.
     *
     * @param connection     connection to the exasol database
     * @param schemaOverride if null EXA_STATISITCS is used. This parameter allows you to test this connector with a
     *                       predefined SCHEMA instead of the unmodifiable live statistics.
     */
    public ExasolStatisticsTableRegularMetricReader(final Connection connection, final String schemaOverride) {
        super(connection, schemaOverride);
    }

    @Override
    public List<ExasolMetricDatum> readMetrics(final Collection<String> metrics, final Instant ofMinute) {
        final List<ExasolStatisticsTableRegularMetric> parsedMetrics = metrics.stream()
                .map(ExasolStatisticsTableRegularMetric::valueOf).collect(Collectors.toList());
        final Instant start = ofMinute.truncatedTo(ChronoUnit.MINUTES);
        final Instant end = start.plus(Duration.ofMinutes(1));
        assertIntervalIsInThePastForTheDatabaseTime(end);
        final Stream<ExasolStatisticsTable> requestedTables = parsedMetrics.stream()
                .map(ExasolStatisticsTableRegularMetric::getTable).distinct();
        return requestedTables.flatMap(table -> readMetricsFromTable(parsedMetrics, start, end, table).stream())
                .collect(Collectors.toList());
    }

    private List<ExasolMetricDatum> readMetricsFromTable(final List<ExasolStatisticsTableRegularMetric> metrics,
            final Instant start, final Instant end, final ExasolStatisticsTable statisticsTable) {
        final List<ExasolStatisticsTableRegularMetric> currentTablesMetrics = metrics.stream()
                .filter(metric -> metric.getTable().equals(statisticsTable)).collect(Collectors.toList());
        final String query = buildMetricsQuery(statisticsTable);
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setTimestamp(1, Timestamp.from(start), this.utcCalendar);
            statement.setTimestamp(2, Timestamp.from(end), this.utcCalendar);
            return executeSystemTableQuery(currentTablesMetrics, statement);
        } catch (final SQLException exception) {
            if (ExceptionClassifier.isAmbiguousTimestampException(exception)) {
                LOGGER.warn(ExaError.messageBuilder("W-CWA-12").message("Skipping points due to timeshift. ").message(
                        "Since the Exasol database stores the logs with dates in the DBTIMEZONE there are ambiguous logs during the timeshift.")
                        .mitigation("The only thing you can do is to change your DBTIMEZONE to UTC.").toString());
                return emptyList();
            } else {
                throw wrapSqlException(query, exception);
            }
        }
    }

    protected String buildMetricsQuery(final ExasolStatisticsTable statisticsTable) {
        return "SELECT "
                + "CONVERT_TZ(MEASURE_TIME, DBTIMEZONE, 'UTC', 'INVALID REJECT AMBIGUOUS REJECT') as UTC_MEASURE_TIME, t.* "
                + "FROM \"" + getSchema() + "\".\"" + statisticsTable + "\"" + " t "
                + "WHERE MEASURE_TIME >= CONVERT_TZ(?, 'UTC', DBTIMEZONE, 'INVALID REJECT AMBIGUOUS REJECT') "
                + "AND MEASURE_TIME < CONVERT_TZ(?, 'UTC', DBTIMEZONE, 'INVALID REJECT AMBIGUOUS REJECT');";
    }

    private IllegalStateException wrapSqlException(final String query, final SQLException exception) {
        return new IllegalStateException(
                ExaError.messageBuilder("F-CWA-1").message("Failed to execute query ({{query}}) on system table.")
                        .parameter("query", query).ticketMitigation().toString(),
                exception);
    }

    private List<ExasolMetricDatum> executeSystemTableQuery(
            final List<ExasolStatisticsTableRegularMetric> currentTablesMetrics, final PreparedStatement statement)
            throws SQLException {
        try (final ResultSet resultSet = statement.executeQuery()) {
            final List<ExasolMetricDatum> result = new ArrayList<>();
            while (resultSet.next()) {
                for (final ExasolStatisticsTableRegularMetric metric : currentTablesMetrics) {
                    result.add(new ExasolMetricDatum(metric.name(), metric.getUnit(),
                            resultSet.getTimestamp("UTC_MEASURE_TIME", this.utcCalendar).toInstant(),
                            resultSet.getDouble(metric.name()), resultSet.getString("CLUSTER_NAME")));
                }
            }
            return result;
        }
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
}
