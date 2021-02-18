package com.exasol.cloudwatch;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exasol.errorreporting.ExaError;

/**
 * This class reads the value of {@link ExasolStatisticsTableMetric}s from the Exasol database.
 */
public class SimpleExasolStatisticsTableMetricReader extends AbstractExasolStatisticsTableMetricReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleExasolStatisticsTableMetricReader.class);

    /**
     * Create a new instance of {@link SimpleExasolStatisticsTableMetricReader}.
     *
     * @param connection     connection to the exasol database
     * @param schemaOverride if null EXA_STATISITCS is used. This parameter allows you to test this connector with a
     *                       predefined SCHEMA instead of the unmodifiable live statistics.
     */
    public SimpleExasolStatisticsTableMetricReader(final Connection connection, final String schemaOverride) {
        super(connection, schemaOverride);
    }

    @Override
    protected List<ExasolMetricDatum> loadMetricsForTable(final List<ExasolStatisticsTableMetric> metrics,
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
            final List<ExasolStatisticsTableMetric> currentTablesMetrics, final PreparedStatement statement)
            throws SQLException {
        try (final ResultSet resultSet = statement.executeQuery()) {
            final List<ExasolMetricDatum> result = new ArrayList<>();
            while (resultSet.next()) {
                for (final ExasolStatisticsTableMetric metric : currentTablesMetrics) {
                    result.add(new ExasolMetricDatum(metric,
                            resultSet.getTimestamp("UTC_MEASURE_TIME", this.utcCalendar).toInstant(),
                            resultSet.getDouble(metric.name()), resultSet.getString("CLUSTER_NAME")));
                }
            }
            return result;
        }
    }
}
