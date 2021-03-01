package com.exasol.cloudwatch.exasolmetrics;

import java.time.Instant;
import java.util.Objects;

/**
 * This class represents one single measurement for a {@link ExasolStatisticsTableRegularMetric}.
 */
public class ExasolMetricDatum {
    private final String metricName;
    private final ExasolUnit unit;
    private final Instant timestamp;
    private final double value;
    private final String clusterName;

    /**
     * Create a new instance of {@link ExasolMetricDatum}.
     * 
     * @param metricName  metric the datapoint belongs to
     * @param unit        unit of the metric
     * @param timestamp   timestamp of the measurement
     * @param value       value
     * @param clusterName name of the cluster
     */
    public ExasolMetricDatum(final String metricName, final ExasolUnit unit, final Instant timestamp,
            final double value, final String clusterName) {
        this.metricName = metricName;
        this.unit = unit;
        this.timestamp = timestamp;
        this.value = value;
        this.clusterName = clusterName;
    }

    /**
     * Get the timestamp.
     * 
     * @return timestamp
     */
    public Instant getTimestamp() {
        return this.timestamp;
    }

    /**
     * Get the value.
     * 
     * @return value
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Get the cluster name.
     * 
     * @return cluster name
     */
    public String getClusterName() {
        return this.clusterName;
    }

    /**
     * Get the metric.
     * 
     * @return metric
     */
    public String getMetricName() {
        return this.metricName;
    }

    /**
     * Get the unit.
     *
     * @return unit
     */
    public ExasolUnit getUnit() {
        return this.unit;
    }

    @Override
    public String toString() {
        return "ExasolMetricDatum{" + "metricName=" + this.metricName + ", unit=" + this.unit + ", timestamp="
                + this.timestamp + ", value=" + this.value + '}';
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExasolMetricDatum)) {
            return false;
        }
        final ExasolMetricDatum that = (ExasolMetricDatum) other;
        return this.timestamp.equals(that.timestamp) && this.value == that.value
                && this.metricName.equals(that.metricName) && this.clusterName.equals(that.clusterName)
                && this.unit.equals(that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.timestamp, this.value, this.metricName, this.unit, this.clusterName);
    }
}
