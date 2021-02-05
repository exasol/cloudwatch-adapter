package com.exasol.cloudwatch;

import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

/**
 * {@link AwsClientFactory} that uses the system configuration. In production it uses the credentials from the
 * environment in the Lambda function.
 */
public class DefaultAwsClientFactory implements AwsClientFactory {
    @Override
    public CloudWatchClient getCloudWatchClient() {
        return CloudWatchClient.builder().build();
    }

    @Override
    public SecretsManagerClient getSecretsManagerClient() {
        return SecretsManagerClient.builder().build();
    }
}
