package com.exasol.cloudwatch;

/**
 * Interface for metrics that can be read from the exasol database.
 */
public interface ExasolMetric {
    /**
     * Get the unit of this metric.
     *
     * @return unit of this metric
     */
    public ExasolUnit getUnit();

    /**
     * Get the metric. The name is used as name for the cloudwatch metric and as configuration option for the adapter.
     * 
     * @return name of the metric.
     */
    public String getName();
}
