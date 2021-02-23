package com.exasol.cloudwatch.configuration;

import java.util.*;
import java.util.stream.Collectors;

import com.exasol.errorreporting.ExaError;

/**
 * This class reads configuration properties from the environment variables of the Lambda function.
 */
class EnvironmentConfigurationReader {
    private static final String DEPLOYMENT_NAME_MITIGATION = "Set the EXASOL_DEPLOYMENT_NAME environment variable in your lambda settings to a name describing the exasol installation you want to monitor.";
    private static final String EXASOL_DEPLOYMENT_NAME = "EXASOL_DEPLOYMENT_NAME";
    private static final String EXASOL_CONNECTION_SECRET_ARN = "EXASOL_CONNECTION_SECRET_ARN";

    /**
     * Read {@code EXASOL_DEPLOYMENT_NAME}.
     * 
     * @return name of the Exasol installation
     */
    String readDeploymentName() {
        final String name = Objects.requireNonNull(System.getenv(EXASOL_DEPLOYMENT_NAME),
                ExaError.messageBuilder("E-CWA-7").message("EXASOL_DEPLOYMENT_NAME environment variable is not set.")
                        .mitigation(DEPLOYMENT_NAME_MITIGATION).toString());
        if (name.isBlank()) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-CWA-8")
                    .message("The EXASOL_DEPLOYMENT_NAME is empty.").mitigation(DEPLOYMENT_NAME_MITIGATION).toString());
        }
        return name;
    }

    /**
     * Read {@code METRICS}.
     * 
     * @param availableMetrics list of available metrics
     * @return list of enabled metrics
     */
    // [impl->dsn~env-var-for-metrics-selection~1]
    List<String> readEnabledMetrics(final Collection<String> availableMetrics) {
        final String metricsParameter = System.getenv("METRICS");
        if (metricsParameter == null || metricsParameter.isBlank()) {
            return List.copyOf(availableMetrics);
        } else {
            return Arrays.stream(metricsParameter.split(",")).map(String::trim).collect(Collectors.toList());
        }
    }

    /**
     * Read {@code EXASOL_CONNECTION_SECRET_ARN}.
     * 
     * @return arn of the secret with the exasol connection information
     */
    String readExasolConnectionArn() {
        return Objects.requireNonNull(System.getenv(EXASOL_CONNECTION_SECRET_ARN), ExaError.messageBuilder("E-CWA-15")
                .message("EXASOL_CONNECTION_SECRET_ARN environment variable was not set.")
                .mitigation(
                        "Add your Exasol connection details to AWS SecretsManager and store the arn of the secret in the environment variable EXASOL_CONNECTION_SECRET_ARN of this Lambda function.")
                .toString());
    }
}
