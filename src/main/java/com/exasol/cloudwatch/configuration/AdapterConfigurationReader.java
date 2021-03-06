package com.exasol.cloudwatch.configuration;

import java.util.Collection;

import com.exasol.cloudwatch.AwsClientFactory;

/**
 * This is a reader for {@link AdapterConfiguration}.
 */
public class AdapterConfigurationReader {
    private final AwsClientFactory awsClientFactory;

    /**
     * Create a new instance of {@link AdapterConfigurationReader}.
     * 
     * @param awsClientFactory dependency injection of the AWS client
     */
    public AdapterConfigurationReader(final AwsClientFactory awsClientFactory) {
        this.awsClientFactory = awsClientFactory;
    }

    /**
     * Read a {@link AdapterConfiguration}.
     * 
     * @param availableMetrics list of available metrics
     * @return read {@link AdapterConfiguration}
     */
    public AdapterConfiguration readConfiguration(final Collection<String> availableMetrics) {
        final EnvironmentConfigurationReader envConfReader = new EnvironmentConfigurationReader();
        final String secretArn = envConfReader.readExasolConnectionArn();
        final ExasolCredentials exasolCredentials = new ExasolCredentialsReader(this.awsClientFactory)
                .readExasolCredentials(secretArn);
        return new AdapterConfiguration(envConfReader.readDeploymentName(),
                envConfReader.readEnabledMetrics(availableMetrics), exasolCredentials);
    }
}
