package com.exasol.cloudwatch.configuration;

import java.util.Collection;

import com.exasol.cloudwatch.AwsClientFactory;

/**
 * This is a reader for {@link AdapterConfiguration}.
 */
public class AdapterConfigurationReader {
    private final AwsClientFactory awsClientFactory;
    private final EnvironmentVariableProvider environmentVariableProvider;

    /**
     * Create a new instance of {@link AdapterConfigurationReader}.
     *
     * @param awsClientFactory            dependency injection of the AWS client
     * @param environmentVariableProvider dependency injection of the {@link EnvironmentConfigurationReader}
     */
    public AdapterConfigurationReader(final AwsClientFactory awsClientFactory,
            final EnvironmentVariableProvider environmentVariableProvider) {
        this.awsClientFactory = awsClientFactory;
        this.environmentVariableProvider = environmentVariableProvider;
    }

    /**
     * Read a {@link AdapterConfiguration}.
     *
     * @param availableMetrics list of available metrics
     * @return read {@link AdapterConfiguration}
     */
    public AdapterConfiguration readConfiguration(final Collection<String> availableMetrics) {
        final EnvironmentConfigurationReader envConfReader = new EnvironmentConfigurationReader(
                this.environmentVariableProvider);
        final String secretArn = envConfReader.readExasolConnectionArn();
        final ExasolCredentials exasolCredentials = new ExasolCredentialsReader(this.awsClientFactory)
                .readExasolCredentials(secretArn);
        return new AdapterConfiguration(envConfReader.readDeploymentName(),
                envConfReader.readEnabledMetrics(availableMetrics), exasolCredentials);
    }
}
