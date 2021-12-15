package com.exasol.cloudwatch.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnvironmentConfigurationReaderTest {
    private static final List<String> AVAILABLE_METRICS = List.of("MY_METRIC");
    private EnvironmentConfigurationReader READER;
    private MockEnvironmentVariableProvider mockEnvironment;

    @BeforeEach
    void beforeEach() {
        this.mockEnvironment = new MockEnvironmentVariableProvider();
        this.READER = new EnvironmentConfigurationReader(this.mockEnvironment);
    }

    @Test
    // [utest->dsn~env-var-for-metrics-selection~1]
    void testMetrics() {
        this.mockEnvironment.put("METRICS", "CPU, USERS");
        assertThat(this.READER.readEnabledMetrics(AVAILABLE_METRICS), containsInAnyOrder("CPU", "USERS"));
    }

    @Test
    void testMetricsNotSet() {
        assertThat(this.READER.readEnabledMetrics(AVAILABLE_METRICS),
                containsInAnyOrder(AVAILABLE_METRICS.toArray(String[]::new)));
    }

    @Test
    void testMetricsEmpty() {
        this.mockEnvironment.put("METRICS", "");
        assertThat(this.READER.readEnabledMetrics(AVAILABLE_METRICS),
                containsInAnyOrder(AVAILABLE_METRICS.toArray(String[]::new)));
    }

    @Test
    void testDeploymentName() {
        this.mockEnvironment.put("EXASOL_DEPLOYMENT_NAME", "MyExasol");
        assertThat(this.READER.readDeploymentName(), equalTo("MyExasol"));
    }

    @Test
    void testDeploymentNameMissing() {
        final NullPointerException exception = assertThrows(NullPointerException.class,
                this.READER::readDeploymentName);
        assertThat(exception.getMessage(), containsString("E-CWA-7"));
    }

    @Test
    void testEmptyDeploymentName() {
        this.mockEnvironment.put("EXASOL_DEPLOYMENT_NAME", "");
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                this.READER::readDeploymentName);
        assertThat(exception.getMessage(), containsString("E-CWA-8"));
    }
}