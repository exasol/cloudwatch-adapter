package com.exasol.cloudwatch.configuration;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class EnvironmentConfigurationReaderTest {
    private static final EnvironmentConfigurationReader READER = new EnvironmentConfigurationReader();
    private static final List<String> AVAILABLE_METRICS = List.of("MY_METRIC");

    @Test
    // [utest->dsn~env-var-for-metrics-selection~1]
    void testMetrics() throws Exception {
        withEnvironmentVariable("METRICS", "CPU, USERS").execute(() -> {
            assertThat(READER.readEnabledMetrics(AVAILABLE_METRICS), Matchers.containsInAnyOrder("CPU", "USERS"));
        });
    }

    @Test
    void testMetricsNotSet() {
        assertThat(READER.readEnabledMetrics(AVAILABLE_METRICS),
                containsInAnyOrder(AVAILABLE_METRICS.toArray(String[]::new)));
    }

    @Test
    void testMetricsEmpty() throws Exception {
        withEnvironmentVariable("METRICS", "").execute(() -> {
            assertThat(READER.readEnabledMetrics(AVAILABLE_METRICS),
                    containsInAnyOrder(AVAILABLE_METRICS.toArray(String[]::new)));
        });
    }

    @Test
    void testDeploymentName() throws Exception {
        withEnvironmentVariable("EXASOL_DEPLOYMENT_NAME", "MyExasol").execute(() -> {
            assertThat(READER.readDeploymentName(), equalTo("MyExasol"));
        });
    }

    @Test
    void testDeploymentNameMissing() {
        final NullPointerException exception = assertThrows(NullPointerException.class, READER::readDeploymentName);
        assertThat(exception.getMessage(), containsString("E-CWA-7"));
    }

    @Test
    void testEmptyDeploymentName() throws Exception {
        withEnvironmentVariable("EXASOL_DEPLOYMENT_NAME", "").execute(() -> {
            final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    READER::readDeploymentName);
            assertThat(exception.getMessage(), containsString("E-CWA-8"));
        });
    }
}