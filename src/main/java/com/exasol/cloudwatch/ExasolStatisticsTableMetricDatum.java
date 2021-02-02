package com.exasol.cloudwatch;

import java.time.Instant;
import java.util.Objects;

/**
 * This class represents one single measurement for a {@link ExasolStatisticsTableMetric}.
 */
public class ExasolStatisticsTableMetricDatum {
    private final ExasolStatisticsTableMetric metric;
    private final Instant timestamp;
    private final double value;
    private final String clusterName;

    /**
     * Create a new instance of {@link ExasolStatisticsTableMetricDatum}.
     * 
     * @param metric      metric the datapoint belongs to
     * @param timestamp   timestamp of the measurement
     * @param value       value
     * @param clusterName name of the cluster
     */
    public ExasolStatisticsTableMetricDatum(final ExasolStatisticsTableMetric metric, final Instant timestamp,
            final double value, final String clusterName) {
        this.metric = metric;
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
    public ExasolStatisticsTableMetric getMetric() {
        return this.metric;
    }

    @Override
    public String toString() {
        return "ExasolStatisticsTableMetricDatum{" + "metric=" + this.metric + ", timestamp=" + this.timestamp
                + ", value=" + this.value + '}';
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExasolStatisticsTableMetricDatum)) {
            return false;
        }
        final ExasolStatisticsTableMetricDatum that = (ExasolStatisticsTableMetricDatum) other;
        return this.timestamp.equals(that.timestamp) && this.value == that.value && this.metric.equals(that.metric)
                && this.clusterName.equals(that.clusterName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.timestamp, this.value, this.metric, this.clusterName);
    }
}
