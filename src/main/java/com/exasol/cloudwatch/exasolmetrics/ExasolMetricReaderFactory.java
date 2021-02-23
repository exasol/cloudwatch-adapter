package com.exasol.cloudwatch.exasolmetrics;

import java.sql.Connection;
import java.util.Optional;
import java.util.Set;

/**
 * Factory for {@link ExasolMetricReader}. Different factories define different metric sources.
 */
interface ExasolMetricReaderFactory {
    /**
     * Get a list of metrics that this reader can read.
     *
     * @return list of supported metrics
     */
    Set<String> getSupportedMetrics();

    /**
     * Get an {@link ExasolMetricReader} that can read the metrics offered by this class. final Connection connection,
     * final String schemaOverride
     * 
     * @param connection     connection to the exasol database
     * @param schemaOverride if null EXA_STATISTICS is used. This parameter allows you to test this connector with a
     *                       predefined SCHEMA instead of the unmodifiable live statistics.
     * @return {@link ExasolMetricReader}
     */
    ExasolMetricReader getReader(Connection connection, String schemaOverride);

    /**
     * Get a link to the documentation for a specific metric.
     * 
     * @param metric metric
     * @return link to the exasol documentation
     */
    Optional<String> getDocLinkForMetric(String metric);
}
