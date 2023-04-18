package com.exasol.cloudwatch;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.CLOUDWATCH;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SECRETSMANAGER;

import java.io.*;
import java.time.Instant;

import org.testcontainers.containers.localstack.LocalStackContainer;

import jakarta.json.*;
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
        return CloudWatchClient.builder().endpointOverride(container.getEndpointOverride(CLOUDWATCH))
                .region(Region.of(container.getRegion()))
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials.create(container.getAccessKey(), container.getSecretKey())))
                .build();
    }

    @Override
    public SecretsManagerClient getSecretsManagerClient() {
        return SecretsManagerClient.builder().endpointOverride(this.container.getEndpointOverride(SECRETSMANAGER))
                .region(Region.of(container.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(this.container.getAccessKey(), this.container.getSecretKey())))
                .build();
    }

    public String putExasolCredentials(final String host, final String port, final String username,
            final String password, final String certificateFingerprint) throws IOException {
        final JsonObjectBuilder jsonBuilder = Json.createObjectBuilder().add("username", username)
                .add("password", password).add("host", host).add("port", port);
        if (certificateFingerprint != null) {
            jsonBuilder.add("certificateFingerprint", certificateFingerprint);
        }
        final JsonObject jsonCredentials = jsonBuilder.build();
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
