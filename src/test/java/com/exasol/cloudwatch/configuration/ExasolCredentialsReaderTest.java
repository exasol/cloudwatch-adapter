package com.exasol.cloudwatch.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SECRETSMANAGER;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.exasol.cloudwatch.LocalStackTestInterface;
import com.exasol.cloudwatch.LocalstackContainerWithReuse;

@Testcontainers
class ExasolCredentialsReaderTest {
    @Container
    private static final LocalStackContainer LOCAL_STACK_CONTAINER = new LocalstackContainerWithReuse(
            DockerImageName.parse("localstack/localstack:latest")).withServices(SECRETSMANAGER);
    private static LocalStackTestInterface localStackTestInterface;
    private static ExasolCredentialsReader credentialsReader;

    @BeforeAll
    static void beforeAll() {
        localStackTestInterface = new LocalStackTestInterface(LOCAL_STACK_CONTAINER);
        credentialsReader = new ExasolCredentialsReader(localStackTestInterface);
    }

    @Test
    void testReadCredentials() throws IOException {
        final String secretArn = localStackTestInterface.putExasolCredentials("127.0.0.1", "1234", "test", "myPass");
        try {
            final ExasolCredentials exasolCredentials = credentialsReader.readExasolCredentials(secretArn);
            assertAll(() -> assertThat(exasolCredentials.getHost(), equalTo("127.0.0.1")),
                    () -> assertThat(exasolCredentials.getPort(), equalTo("1234")),
                    () -> assertThat(exasolCredentials.getUser(), equalTo("test")),
                    () -> assertThat(exasolCredentials.getPass(), equalTo("myPass")));
        } finally {
            localStackTestInterface.deleteSecret(secretArn);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "username", "password", "host" })
    void testMissingProperties(final String missingProperty) throws IOException {
        final JsonObject jsonObject = Json.createObjectBuilder().add("username", "user").add("password", "pass")
                .add("host", "127.0.0.1").add("port", "123")//
                .remove(missingProperty).build();
        final String secretArn = localStackTestInterface.putExasolCredentials(jsonObject);
        try {
            final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> credentialsReader.readExasolCredentials(secretArn));
            assertAll(//
                    () -> assertThat(exception.getMessage(), containsString(missingProperty)),
                    () -> assertThat(exception.getMessage(), containsString(secretArn)),
                    () -> assertThat(exception.getMessage(), containsString("E-CWA-16"))//
            );
        } finally {
            localStackTestInterface.deleteSecret(secretArn);
        }
    }

    @Test
    void testDefaultPort() throws IOException {
        final JsonObject jsonObject = Json.createObjectBuilder().add("username", "user").add("password", "pass")
                .add("host", "127.0.0.1").build();
        final String secretArn = localStackTestInterface.putExasolCredentials(jsonObject);
        try {
            final ExasolCredentials exasolCredentials = credentialsReader.readExasolCredentials(secretArn);
            assertThat(exasolCredentials.getPort(), equalTo("8563"));
        } finally {
            localStackTestInterface.deleteSecret(secretArn);
        }
    }
}