package com.exasol.cloudwatch.configuration;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.json.*;

import com.exasol.cloudwatch.AwsClientFactory;
import com.exasol.errorreporting.ExaError;

import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

/**
 * This class reads the Exasol credentials from AWS SecretsManager.
 */
class ExasolCredentialsReader {
    private static final String KEY_USER = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PORT = "port";
    private static final String KEY_HOST = "host";
    private static final String KEY_CERTIFICATE_FINGERPRINT = "certificateFingerprint";
    private static final String DEFAULT_CERTIFICATE_FINGERPRINT = null;
    private final AwsClientFactory awsClientFactory;

    /**
     * Create a new instance of {@link ExasolCredentialsReader}.
     *
     * @param awsClientFactory dependency injection of AWS client
     */
    public ExasolCredentialsReader(final AwsClientFactory awsClientFactory) {
        this.awsClientFactory = awsClientFactory;
    }

    /**
     * Read the Exasol credentials from AWS SecretsManager.
     *
     * @param secretArn arn of the secret
     * @return read {@link ExasolCredentials}
     */
    // [impl->dsn~exasol-credentials-from-secrets-manager~1]
    ExasolCredentials readExasolCredentials(final String secretArn) {
        try (final SecretsManagerClient secretsManagerClient = this.awsClientFactory.getSecretsManagerClient()) {
            return readExasolCredentials(secretArn, secretsManagerClient);
        }
    }

    private ExasolCredentials readExasolCredentials(final String secretArn,
            final SecretsManagerClient secretsManagerClient) {
        final String secret = fetchSecret(secretArn, secretsManagerClient);
        try (final JsonReader reader = Json.createReader(new StringReader(secret))) {
            final JsonObject secretsObject = reader.readObject();
            validateSecretContainsRequiredFields(secretArn, secretsObject);
            return new ExasolCredentials(secretsObject.getString(KEY_HOST), secretsObject.getString(KEY_PORT, "8563"),
                    secretsObject.getString(KEY_USER), secretsObject.getString(KEY_PASSWORD),
                    secretsObject.getString(KEY_CERTIFICATE_FINGERPRINT, DEFAULT_CERTIFICATE_FINGERPRINT));
        }
    }

    private String fetchSecret(final String secretArn, final SecretsManagerClient secretsManagerClient) {
        try {
            return secretsManagerClient.getSecretValue(GetSecretValueRequest.builder().secretId(secretArn).build())
                    .secretString();
        } catch (final SdkClientException exception) {
            throw new IllegalStateException(
                    ExaError.messageBuilder("E-CWA-18").message("Failed to fetch secret from AWS SecretManager.")
                            .mitigation("Make sure that you created a SecretsManager endpoint in your VPC.").toString(),
                    exception);
        }
    }

    private void validateSecretContainsRequiredFields(final String secretArn, final JsonObject secretsObject) {
        final List<String> missingKeys = Stream.of(KEY_USER, KEY_PASSWORD, KEY_HOST)
                .filter(key -> !secretsObject.containsKey(key)).collect(Collectors.toList());
        if (!missingKeys.isEmpty()) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-CWA-16")
                    .message("Missing required fields {{missing fields}} in secret key {{key arn}}.")
                    .mitigation("Fill the missing fields in AWS SecretsManager.")
                    .parameter("missing fields", missingKeys).parameter("key arn", secretArn).toString());
        }
    }
}
