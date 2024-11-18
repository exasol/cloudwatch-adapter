package com.exasol.cloudwatch.configuration;

import static com.exasol.cloudwatch.TestConstants.LOCAL_STACK_IMAGE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SECRETSMANAGER;

import java.io.IOException;
import java.util.Collections;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.exasol.cloudwatch.LocalStackTestInterface;
import com.exasol.cloudwatch.LocalstackContainerWithReuse;

@Testcontainers
class AdapterConfigurationReaderIT {

    @Container
    @SuppressWarnings("resource") // Will be closed by @Testcontainers
    private static final LocalStackContainer LOCAL_STACK_CONTAINER = new LocalstackContainerWithReuse(
            DockerImageName.parse(LOCAL_STACK_IMAGE)).withServices(SECRETSMANAGER);
    private static LocalStackTestInterface localStackTestInterface;

    private MockEnvironmentVariableProvider mockEnvironment;
    private AdapterConfigurationReader reader;

    @BeforeAll
    static void beforeAll() {
        localStackTestInterface = new LocalStackTestInterface(LOCAL_STACK_CONTAINER);
    }

    @BeforeEach
    void beforeEach() {
        this.mockEnvironment = new MockEnvironmentVariableProvider();
        this.reader = new AdapterConfigurationReader(localStackTestInterface, this.mockEnvironment);
    }

    @Test
    void testReadConfiguration() throws IOException {
        final String secretArn = localStackTestInterface.putExasolCredentials("127.0.0.1", "1234", "test", "myPass",
                "myFingerprint");
        this.mockEnvironment.put("EXASOL_DEPLOYMENT_NAME", "MyExasol");
        this.mockEnvironment.put("EXASOL_CONNECTION_SECRET_ARN", secretArn);
        this.mockEnvironment.put("METRICS", "USERS");
        try {
            final AdapterConfiguration configuration = this.reader.readConfiguration(Collections.emptyList());
            final ExasolCredentials exasolCredentials = configuration.getExasolCredentials();
            assertAll(() -> assertThat(exasolCredentials.getHost(), equalTo("127.0.0.1")),
                    () -> assertThat(exasolCredentials.getPort(), equalTo("1234")),
                    () -> assertThat(exasolCredentials.getUser(), equalTo("test")),
                    () -> assertThat(exasolCredentials.getPass(), equalTo("myPass")),
                    () -> assertThat(exasolCredentials.getCertificateFingerprint(), equalTo("myFingerprint")),
                    () -> assertThat(configuration.getEnabledMetrics(), containsInAnyOrder("USERS")),
                    () -> assertThat(configuration.getDeploymentName(), equalTo("MyExasol")));
        } finally {
            localStackTestInterface.deleteSecret(secretArn);
        }
    }

    @Test
    void testReadConfigurationWithMissingFingerprint() throws IOException {
        final String secretArn = localStackTestInterface.putExasolCredentials("127.0.0.1", "1234", "test", "myPass",
                null);
        this.mockEnvironment.put("EXASOL_DEPLOYMENT_NAME", "MyExasol");
        this.mockEnvironment.put("EXASOL_CONNECTION_SECRET_ARN", secretArn);
        this.mockEnvironment.put("METRICS", "USERS");
        try {
            final AdapterConfiguration configuration = this.reader.readConfiguration(Collections.emptyList());
            final ExasolCredentials exasolCredentials = configuration.getExasolCredentials();
            assertAll(() -> assertThat(exasolCredentials.getHost(), equalTo("127.0.0.1")),
                    () -> assertThat(exasolCredentials.getPort(), equalTo("1234")),
                    () -> assertThat(exasolCredentials.getUser(), equalTo("test")),
                    () -> assertThat(exasolCredentials.getPass(), equalTo("myPass")),
                    () -> assertThat(exasolCredentials.getCertificateFingerprint(), nullValue()),
                    () -> assertThat(configuration.getEnabledMetrics(), containsInAnyOrder("USERS")),
                    () -> assertThat(configuration.getDeploymentName(), equalTo("MyExasol")));
        } finally {
            localStackTestInterface.deleteSecret(secretArn);
        }
    }
}
