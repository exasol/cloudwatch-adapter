package com.exasol.cloudwatch;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.exasol.errorreporting.ExaError;

/**
 * This class reads the value of {@link ExasolStatisticsTableMetric}s from the Exasol database for tables like
 * {@code EXA_SYSTEM_EVENTS}. Such tables don't have recurring snapshots of the data.
 * 
 */
public class LastValueExasolStatisticsTableMetricReader extends AbstractExasolStatisticsTableMetricReader {

    /**
     * Create a new instance of {@link LastValueExasolStatisticsTableMetricReader}.
     * 
     * @param connection     connection to the exasol database
     * @param schemaOverride if null EXA_STATISTICS is used. This parameter allows you to test this connector with a
     *                       predefined SCHEMA instead of the unmodifiable live statistics.
     */
    public LastValueExasolStatisticsTableMetricReader(final Connection connection, final String schemaOverride) {
        super(connection, schemaOverride);
    }

    @Override
    protected List<ExasolMetricDatum> loadMetricsForTable(final List<ExasolStatisticsTableMetric> metrics,
            final Instant start, final Instant end, final ExasolStatisticsTable statisticsTable) {
        final String query = buildMetricsQuery(statisticsTable);
        try (final PreparedStatement statement = this.connection.prepareStatement(query);
                final ResultSet resultSet = statement.executeQuery()) {
            return readResult(metrics, resultSet);
        } catch (final SQLException exception) {
            throw new IllegalStateException(
                    ExaError.messageBuilder("F-CWA-17").message("Failed to fetch metrics ({{query}}).")
                            .parameter("query", query).ticketMitigation().toString(),
                    exception);
        }
    }

    private List<ExasolMetricDatum> readResult(final List<ExasolStatisticsTableMetric> metrics,
            final ResultSet resultSet) throws SQLException {
        final List<ExasolMetricDatum> result = new ArrayList<>();
        while (resultSet.next()) {
            for (final ExasolStatisticsTableMetric metric : metrics) {
                result.add(new ExasolMetricDatum(metric, Instant.now(), resultSet.getDouble(metric.name()),
                        resultSet.getString("CLUSTER_NAME")));
            }
        }
        return result;
    }

    /**
     * Build a metric query for the latest report(row) of each cluster.
     * 
     * @param statisticsTable statistics table
     * @return sql query
     */
    private String buildMetricsQuery(final ExasolStatisticsTable statisticsTable) {
        final String tableName = "\"" + getSchema() + "\".\"" + statisticsTable + "\"";
        return "SELECT t.* " + "FROM " + tableName + " t "
                + "JOIN (SELECT MAX(MEASURE_TIME) as MAX_MEASURE_TIME, CLUSTER_NAME FROM " + tableName
                + " GROUP BY CLUSTER_NAME) as latest ON latest.MAX_MEASURE_TIME = t.MEASURE_TIME AND latest.CLUSTER_NAME = t.CLUSTER_NAME;";
    }
}
