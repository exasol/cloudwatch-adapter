package com.exasol.cloudwatch;

import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

/**
 * Factory interface for AWS client dependency injection.
 */
public interface AwsClientFactory {

    /**
     * Get an AWS CloudWatch client.
     * 
     * @return AWS CloudWatch client
     */
    public CloudWatchClient getCloudWatchClient();

    /**
     * Get an AWS SecretsManager client.
     * 
     * @return AWS SecretsManager client
     */
    public SecretsManagerClient getSecretsManagerClient();

}
