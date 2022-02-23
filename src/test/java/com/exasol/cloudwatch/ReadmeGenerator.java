package com.exasol.cloudwatch;

import java.util.stream.Collectors;

import com.exasol.cloudwatch.exasolmetrics.ExasolMetricProvider;

import org.junit.jupiter.api.Test;

@SuppressWarnings("java:S3577") // ame does not contain Test since not a real test
class ReadmeGenerator {

    /**
     * This is not a test but a tool to generate the list of Metrics for the readme file.
     */
    @SuppressWarnings("java:S2699") // no assertions since not a test
    @Test
    void printMetrics() {
        final ExasolMetricProvider exasolMetricProvider = new ExasolMetricProvider();
        System.out.println("* " + exasolMetricProvider.getSupportedMetrics().stream()
                .map(metric -> this.buildMarkdownLink(metric, exasolMetricProvider))
                .collect(Collectors.joining("\n* ")));
    }

    private String buildMarkdownLink(final String metric, final ExasolMetricProvider exasolMetricProvider) {
        return exasolMetricProvider.getDocLinkForMetric(metric).map(s -> "[`" + metric + "`](" + s + ")")
                .orElseGet(() -> "`" + metric + "`");
    }
}
