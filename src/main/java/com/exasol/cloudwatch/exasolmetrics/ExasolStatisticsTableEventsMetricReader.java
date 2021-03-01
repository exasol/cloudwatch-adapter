package com.exasol.cloudwatch.exasolmetrics;

import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import com.exasol.errorreporting.ExaError;

/**
 * This class reads the value of {@link ExasolStatisticsTableEventsMetricReader}s from the Exasol database for tables
 * like {@code EXA_SYSTEM_EVENTS}. This is required because such tables do not provide the recurring snapshots of the
 * data.
 */
class ExasolStatisticsTableEventsMetricReader extends AbstractExasolStatisticsTableMetricReader {

    /**
     * Create a new instance of {@link ExasolStatisticsTableEventsMetricReader}.
     * 
     * @param connection     connection to the exasol database
     * @param schemaOverride if null EXA_STATISTICS is used. This parameter allows you to test this connector with a
     *                       predefined SCHEMA instead of the unmodifiable live statistics.
     */
    public ExasolStatisticsTableEventsMetricReader(final Connection connection, final String schemaOverride) {
        super(connection, schemaOverride);
    }

    // [impl->dsn~report-minute-before-event~1]
    @Override
    public List<ExasolMetricDatum> readMetrics(final Collection<String> metrics, final Instant ofMinute) {
        final List<ExasolStatisticsTableEventsMetric> parsedMetrics = getMetricsToReadInCurrentMinute(metrics,
                ofMinute);
        final String query = buildMetricsQuery();
        try (final PreparedStatement statement = this.connection.prepareStatement(query);
                final ResultSet resultSet = statement.executeQuery()) {
            return readResult(parsedMetrics, resultSet);
        } catch (final SQLException exception) {
            throw new IllegalStateException(
                    ExaError.messageBuilder("F-CWA-17").message("Failed to fetch metrics ({{query}}).")
                            .parameter("query", query).ticketMitigation().toString(),
                    exception);
        }
    }

    private List<ExasolStatisticsTableEventsMetric> getMetricsToReadInCurrentMinute(final Collection<String> metrics,
            final Instant ofMinute) {
        final List<ExasolStatisticsTableEventsMetric> parsedMetrics = metrics.stream()
                .map(ExasolStatisticsTableEventsMetric::valueOf).collect(Collectors.toList());
        return parsedMetrics.stream().filter(metric -> isInThisMinuteReadingRequired(ofMinute, metric))
                .collect(Collectors.toList());
    }

    private boolean isInThisMinuteReadingRequired(final Instant ofMinute,
            final ExasolStatisticsTableEventsMetric metric) {
        final long epochMinute = ofMinute.getEpochSecond() / 60;
        return epochMinute % metric.getReportIntervalMinutes() == 0;
    }

    private List<ExasolMetricDatum> readResult(final List<ExasolStatisticsTableEventsMetric> metrics,
            final ResultSet resultSet) throws SQLException {
        final List<ExasolMetricDatum> result = new ArrayList<>();
        while (resultSet.next()) {
            for (final ExasolStatisticsTableEventsMetric metric : metrics) {
                result.add(new ExasolMetricDatum(metric.name(), metric.getUnit(), Instant.now(),
                        resultSet.getDouble(metric.name()), resultSet.getString("CLUSTER_NAME")));
            }
        }
        return result;
    }

    /**
     * Build a metric query for the latest report of each cluster.
     * 
     * @return sql query
     */
    private String buildMetricsQuery() {
        final String tableName = "\"" + getSchema() + "\".\"" + ExasolStatisticsTable.EXA_SYSTEM_EVENTS + "\"";
        return "SELECT t.* " + "FROM " + tableName + " t "
                + "JOIN (SELECT MAX(MEASURE_TIME) as MAX_MEASURE_TIME, CLUSTER_NAME FROM " + tableName
                + " GROUP BY CLUSTER_NAME) as latest ON latest.MAX_MEASURE_TIME = t.MEASURE_TIME AND latest.CLUSTER_NAME = t.CLUSTER_NAME;";
    }
}
