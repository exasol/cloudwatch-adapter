package com.exasol.cloudwatch;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AdapterConfigurationTest {

    private static final AdapterConfiguration CONFIGURATION = new AdapterConfiguration();

    @Test
    // [utest->dsn~env-var-for-metrics-selection~1]
    void testMetrics() throws Exception {
        withEnvironmentVariable("METRICS", "CPU, USERS").execute(() -> {
            assertThat(CONFIGURATION.getEnabledMetrics(),
                    containsInAnyOrder(ExasolStatisticsTableMetric.CPU, ExasolStatisticsTableMetric.USERS));
        });
    }

    @Test
    void testMetricsNotSet() {
        assertThat(CONFIGURATION.getEnabledMetrics(), containsInAnyOrder(ExasolStatisticsTableMetric.values()));
    }

    @Test
    void testMetricsEmpty() throws Exception {
        withEnvironmentVariable("METRICS", "").execute(() -> {
            assertThat(CONFIGURATION.getEnabledMetrics(), containsInAnyOrder());
        });
    }

    @Test
    void testUnknownMetric() throws Exception {
        withEnvironmentVariable("METRICS", "UNKNOWN").execute(() -> {
            final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    CONFIGURATION::getEnabledMetrics);
            assertThat(exception.getMessage(), startsWith("E-CWA-9"));
        });
    }

    @Test
    void testDeploymentName() throws Exception {
        withEnvironmentVariable("EXASOL_DEPLOYMENT_NAME", "MyExasol").execute(() -> {
            assertThat(CONFIGURATION.getDeploymentName(), equalTo("MyExasol"));
        });
    }

    @Test
    void testDeploymentNameMissing() {
        final NullPointerException exception = assertThrows(NullPointerException.class,
                CONFIGURATION::getDeploymentName);
        assertThat(exception.getMessage(), equalTo(
                "E-CWA-7: EXASOL_DEPLOYMENT_NAME environment variable was not set. Set the EXASOL_DEPLOYMENT_NAME environment variable in your lambda settings to a name describing the exasol installation you want to monitor."));
    }

    @Test
    void testEmptyDeploymentName() throws Exception {
        withEnvironmentVariable("EXASOL_DEPLOYMENT_NAME", "").execute(() -> {
            final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    CONFIGURATION::getDeploymentName);
            assertThat(exception.getMessage(), equalTo(
                    "E-CWA-8: The EXASOL_DEPLOYMENT_NAME was empty. Set the EXASOL_DEPLOYMENT_NAME environment variable in your lambda settings to a name describing the exasol installation you want to monitor."));
        });
    }
}