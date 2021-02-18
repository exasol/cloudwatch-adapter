package com.exasol.cloudwatch;

import java.sql.Connection;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class combines the different {@link ExasolMetricReader}s into one. It groups the metrics by the readers that can
 * read them and then uses the other reads to read them in one chuck.
 */
public class MainExasolMetricReader implements ExasolMetricReader {

    private final SimpleExasolStatisticsTableMetricReader simpleExasolStatisticsTableMetricReader;
    private final LastValueExasolStatisticsTableMetricReader lastValueExasolStatisticsTableMetricReader;

    /**
     * Create a new instance of {@link MainExasolMetricReader}.
     *
     * @param connection     connection to the exasol database
     * @param schemaOverride if null EXA_STATISTICS is used. This parameter allows you to test this connector with a
     *                       predefined SCHEMA instead of the unmodifiable live statistics.
     */
    public MainExasolMetricReader(final Connection connection, final String schemaOverride) {
        this.simpleExasolStatisticsTableMetricReader = new SimpleExasolStatisticsTableMetricReader(connection,
                schemaOverride);
        this.lastValueExasolStatisticsTableMetricReader = new LastValueExasolStatisticsTableMetricReader(connection,
                schemaOverride);
    }

    @Override
    public List<ExasolMetricDatum> readMetrics(final List<ExasolStatisticsTableMetric> metrics,
            final Instant ofMinute) {
        final Map<ExasolMetricReader, List<ExasolStatisticsTableMetric>> metricsByReader = groupMetricsByReader(
                metrics);
        return metricsByReader.entrySet().stream()
                .flatMap(entry -> entry.getKey().readMetrics(entry.getValue(), ofMinute).stream())
                .collect(Collectors.toList());
    }

    private Map<ExasolMetricReader, List<ExasolStatisticsTableMetric>> groupMetricsByReader(
            final List<ExasolStatisticsTableMetric> metrics) {
        final Map<ExasolMetricReader, List<ExasolStatisticsTableMetric>> metricsByReader = new HashMap<>();
        metrics.forEach(metric -> {
            final ExasolMetricReader reader = getReaderForMetric(metric);
            if (!metricsByReader.containsKey(reader)) {
                metricsByReader.put(reader, new LinkedList<>());
            }
            metricsByReader.get(reader).add(metric);
        });
        return metricsByReader;
    }

    private ExasolMetricReader getReaderForMetric(final ExasolStatisticsTableMetric metric) {
        if (metric.getTable() == ExasolStatisticsTable.EXA_SYSTEM_EVENTS) {
            return this.lastValueExasolStatisticsTableMetricReader;
        } else {
            return this.simpleExasolStatisticsTableMetricReader;
        }
    }
}
