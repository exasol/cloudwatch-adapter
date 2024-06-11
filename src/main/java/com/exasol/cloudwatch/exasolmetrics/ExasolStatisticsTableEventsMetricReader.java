package com.exasol.cloudwatch.exasolmetrics;

import java.sql.*;
import java.time.Clock;
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
    private final Clock clock;

    /**
     * Create a new instance of {@link ExasolStatisticsTableEventsMetricReader}.
     *
     * @param connection     connection to the exasol database
     * @param schemaOverride if null EXA_STATISTICS is used. This parameter allows you to test this connector with a
     *                       predefined SCHEMA instead of the unmodifiable live statistics.
     */
    public ExasolStatisticsTableEventsMetricReader(final Connection connection, final String schemaOverride) {
        this(connection, schemaOverride, Clock.systemUTC());
    }

    ExasolStatisticsTableEventsMetricReader(final Connection connection, final String schemaOverride,
            final Clock clock) {
        super(connection, schemaOverride);
        this.clock = clock;
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
        return (epochMinute % metric.getReportIntervalMinutes()) == 0;
    }

    private List<ExasolMetricDatum> readResult(final List<ExasolStatisticsTableEventsMetric> metrics,
            final ResultSet resultSet) throws SQLException {
        final Instant now = this.clock.instant();
        final List<ExasolMetricDatum> result = new ArrayList<>();
        final Set<String> availableColumnLabels = getColumnLabels(resultSet);
        while (resultSet.next()) {
            for (final ExasolStatisticsTableEventsMetric metric : metrics) {
                if (!availableColumnLabels.contains(metric.name())) {
                    throw new IllegalStateException(ExaError.messageBuilder("F-CWA-36").message(
                            "Column {{schema name|uq}}.{{table name|uq}}.{{column|uq}} not available for metric {{metric name}}.")
                            .parameter("schema name", getSchema())
                            .parameter("table name", ExasolStatisticsTable.EXA_SYSTEM_EVENTS)
                            .parameter("column", metric.name()).parameter("metric name", metric.name())
                            .mitigation("Ensure that the Exasol DB version supports this metric.").toString());
                }
                result.add(new ExasolMetricDatum(metric.name(), metric.getUnit(), now,
                        resultSet.getDouble(metric.name()), resultSet.getString("CLUSTER_NAME")));
            }
        }
        return result;
    }

    private Set<String> getColumnLabels(final ResultSet resultSet) throws SQLException {
        final Set<String> columnLabels = new HashSet<>();
        final ResultSetMetaData metaData = resultSet.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            columnLabels.add(metaData.getColumnLabel(i));
        }
        return columnLabels;
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
