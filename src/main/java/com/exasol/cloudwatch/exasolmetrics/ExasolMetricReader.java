package com.exasol.cloudwatch.exasolmetrics;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

/**
 * Interface for classes that can read {@link ExasolMetricDatum}s from the Exasol database.
 */
public interface ExasolMetricReader {

    /**
     * Read the {@link ExasolMetricDatum} for given metrics and a given minute.
     *
     * @param metrics  list of metrics to fetch the data for.
     * @param ofMinute minute to read the data for
     * @return list of Exasol metrics
     */
    // [impl->dsn~report-minute-before-event~1]
    List<ExasolMetricDatum> readMetrics(Collection<String> metrics, Instant ofMinute);
}
