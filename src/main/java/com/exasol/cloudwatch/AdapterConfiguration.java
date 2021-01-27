package com.exasol.cloudwatch;

import java.util.*;
import java.util.stream.Collectors;

import com.exasol.errorreporting.ExaError;

/**
 * This class gives access to the configuration of this adapter from the environment variables.
 */
public class AdapterConfiguration {

    private static final String DEPLOYMENT_NAME_MITIGATION = "Set the EXASOL_DEPLOYMENT_NAME environment variable in your lambda settings to a name describing the exasol installation you want to monitor.";

    /**
     * Get the value of EXASOL_DEPLOYMENT_NAME.
     * 
     * @return value of EXASOL_DEPLOYMENT_NAME
     */
    public String getDeploymentName() {
        final String name = Objects.requireNonNull(System.getenv("EXASOL_DEPLOYMENT_NAME"),
                ExaError.messageBuilder("E-CWA-7").message("EXASOL_DEPLOYMENT_NAME environment variable was not set.")
                        .mitigation(DEPLOYMENT_NAME_MITIGATION).toString());
        if (name.isBlank()) {
            throw new IllegalArgumentException(
                    ExaError.messageBuilder("E-CWA-8").message("The EXASOL_DEPLOYMENT_NAME was empty.")
                            .mitigation(DEPLOYMENT_NAME_MITIGATION).toString());
        }
        return name;
    }

    /**
     * Get a list of enabled enabled by METRICS.
     * 
     * @return list of metrics
     */
    // [impl->dsn~env-var-for-metrics-selection~1]
    public List<ExasolStatisticsTableMetric> getEnabledMetrics() {
        final String metricsParameter = System.getenv("METRICS");
        if (metricsParameter == null) {
            return Arrays.asList(ExasolStatisticsTableMetric.values());
        } else {
            return Arrays.stream(metricsParameter.split(",")).map(String::trim).filter(input -> !input.isEmpty())
                    .map(ExasolStatisticsTableMetric::parse).collect(Collectors.toList());
        }
    }

    /**
     * Get the value of EXASOL_HOST.
     * 
     * @return value of EXASOL_HOST
     */
    public String getExasolHost() {
        final String exasolHost;
        exasolHost = Objects.requireNonNull(System.getenv("EXASOL_HOST"),
                ExaError.messageBuilder("E-CWA-3").message("EXASOL_HOST environment variable was not set.").mitigation(
                        "Set the EXASOL_HOST environment variable in your lambda settings to the ip or host-name of your exasol database.")
                        .toString());
        return exasolHost;
    }

    /**
     * Get the value of EXASOL_PORT.
     * 
     * @return value of EXASOL_PORT
     */
    public String getExasolPort() {
        return Objects.requireNonNullElse(System.getenv("EXASOL_PORT"), "8563");
    }

    /**
     * Get the value of EXASOL_USER.
     * 
     * @return value of EXASOL_USER
     */
    public String getExasolUser() {
        return Objects.requireNonNull(System.getenv("EXASOL_USER"),
                ExaError.messageBuilder("E-CWA-4").message("EXASOL_USER environment variable was not set.").mitigation(
                        "Set the EXASOL_USER environment variable in your lambda settings to the user for connecting to your exasol database.")
                        .toString());
    }

    /**
     * Get the value of EXASOL_PASS.
     * 
     * @return value of EXASOL_PASS
     */
    public String getExasolPass() {
        return Objects.requireNonNull(System.getenv("EXASOL_PASS"),
                ExaError.messageBuilder("E-CWA-5").message("EXASOL_PASS environment variable was not set.").mitigation(
                        "Set the EXASOL_PASS environment variable in your lambda settings to the password for connecting to your exasol database.")
                        .toString());
    }
}
