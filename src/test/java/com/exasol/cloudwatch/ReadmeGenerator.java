package com.exasol.cloudwatch;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.exasol.cloudwatch.exasolmetrics.ExasolMetricProvider;

@SuppressWarnings("java:S3577") // ame does not contain Test since not a real test
class ReadmeGenerator {

    /**
     * This is not a test but a tool to generate the list of Metrics for the readme file.
     */
    @SuppressWarnings("java:S2699") // no assertions since not a test
    @Test
    void printMetrics() {
        final ExasolMetricProvider exasolMetricProvider = new ExasolMetricProvider();
        System.out
                .println(exasolMetricProvider.getSupportedMetrics().stream()
                        .map(metric -> exasolMetricProvider.getDocLinkForMetric(metric)
                                .map(s -> "[`" + metric + "`](" + s + ")").orElse(metric))
                        .collect(Collectors.joining(", ")));
    }
}
