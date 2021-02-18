package com.exasol.cloudwatch;

import java.time.Instant;
import java.util.List;

/**
 * Interface for classes that can read {@link ExasolMetricDatum}s for given {@link ExasolStatisticsTableMetric}s from
 * the exasol database.
 */
public interface ExasolMetricReader {
    /**
     * Read the {@link ExasolMetricDatum} for given metrics and a given minute.
     *
     * @param metrics  list of metrics to fetch the data for.
     * @param ofMinute minute to read the data for
     * @return found
     */
    // [impl->dsn~report-minute-before-event~1]
    List<ExasolMetricDatum> readMetrics(List<ExasolStatisticsTableMetric> metrics, Instant ofMinute);
}
