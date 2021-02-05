package com.exasol.cloudwatch.configuration;

import java.util.List;
import java.util.Objects;

import com.exasol.cloudwatch.ExasolStatisticsTableMetric;

/**
 * This class gives access to the configuration of this adapter from the environment variables.
 */
public class AdapterConfiguration {

    private final String deploymentName;
    private final List<ExasolStatisticsTableMetric> enabledMetrics;
    private final ExasolCredentials exasolCredentials;

    /**
     * Create a new instance of {@link AdapterConfiguration}.
     * 
     * @param deploymentName    name of the exasol installation
     * @param enabledMetrics    list of enabled metrics
     * @param exasolCredentials credentials for the Exasol database
     */
    public AdapterConfiguration(final String deploymentName, final List<ExasolStatisticsTableMetric> enabledMetrics,
            final ExasolCredentials exasolCredentials) {
        this.deploymentName = deploymentName;
        this.enabledMetrics = enabledMetrics;
        this.exasolCredentials = exasolCredentials;
    }

    /**
     * Get the value of {@code EXASOL_DEPLOYMENT_NAME}.
     *
     * @return Exasol deployment name value
     */
    public String getDeploymentName() {
        return this.deploymentName;
    }

    /**
     * Get a list of enabled {@code METRICS}.
     *
     * @return list of metrics
     */
    // [impl->dsn~env-var-for-metrics-selection~1]
    public List<ExasolStatisticsTableMetric> getEnabledMetrics() {
        return this.enabledMetrics;
    }

    /**
     * Get the credentials for the Exasol database.
     * 
     * @return credentials for the Exasol database
     */
    public ExasolCredentials getExasolCredentials() {
        return this.exasolCredentials;
    }

    @Override
    public String toString() {
        return "AdapterConfiguration{" + "deploymentName='" + this.deploymentName + '\'' + ", enabledMetrics="
                + this.enabledMetrics + ", exasolCredentials=" + this.exasolCredentials + '}';
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        final AdapterConfiguration that = (AdapterConfiguration) other;
        return Objects.equals(this.deploymentName, that.deploymentName)
                && Objects.equals(this.enabledMetrics, that.enabledMetrics)
                && Objects.equals(this.exasolCredentials, that.exasolCredentials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.deploymentName, this.enabledMetrics, this.exasolCredentials);
    }
}
