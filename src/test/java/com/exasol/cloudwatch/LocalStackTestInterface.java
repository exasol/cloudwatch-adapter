package com.exasol.cloudwatch;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.CLOUDWATCH;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SECRETSMANAGER;

import java.io.*;
import java.time.Instant;

import javax.json.Json;
import javax.json.JsonObject;

import org.testcontainers.containers.localstack.LocalStackContainer;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.CreateSecretRequest;
import software.amazon.awssdk.services.secretsmanager.model.DeleteSecretRequest;

public class LocalStackTestInterface implements AwsClientFactory {

    private final LocalStackContainer container;

    public LocalStackTestInterface(final LocalStackContainer container) {
        this.container = container;
    }

    @Override
    public CloudWatchClient getCloudWatchClient() {
        return CloudWatchClient.builder().endpointOverride(this.container.getEndpointOverride(CLOUDWATCH))
                .region(Region.EU_CENTRAL_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(AwsBasicCredentials.create("someUser", "ignoredAnyway")))
                .build();
    }

    @Override
    public SecretsManagerClient getSecretsManagerClient() {
        return SecretsManagerClient.builder().endpointOverride(this.container.getEndpointOverride(SECRETSMANAGER))
                .region(Region.EU_CENTRAL_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(AwsBasicCredentials.create("someUser", "ignoredAnyway")))
                .build();
    }

    public String putExasolCredentials(final String host, final String port, final String username,
            final String password) throws IOException {
        final JsonObject jsonCredentials = Json.createObjectBuilder().add("username", username)
                .add("password", password).add("host", host).add("port", port).build();
        return putExasolCredentials(jsonCredentials);
    }

    public String putExasolCredentials(final JsonObject jsonCredentials) throws IOException {
        final String secretString = jsonSerialize(jsonCredentials);
        try (final SecretsManagerClient secretsManagerClient = getSecretsManagerClient()) {
            return secretsManagerClient.createSecret(CreateSecretRequest.builder()
                    .name("myCredentials-" + buildUniqueTag()).secretString(secretString).build()).arn();
        }
    }

    public void deleteSecret(final String secretArn) {
        try (final SecretsManagerClient secretsManagerClient = getSecretsManagerClient()) {
            secretsManagerClient.deleteSecret(
                    DeleteSecretRequest.builder().secretId(secretArn).forceDeleteWithoutRecovery(true).build());
        }
    }

    private String buildUniqueTag() {
        final Instant now = Instant.now();
        return "" + now.getEpochSecond() + now.getNano() + Math.random();
    }

    private String jsonSerialize(final JsonObject jsonObject) throws IOException {
        try (final Writer writer = new StringWriter()) {
            Json.createWriter(writer).write(jsonObject);
            return writer.toString();
        }
    }
}
