package com.exasol.cloudwatch;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

@SuppressWarnings("java:S3577") // ame does not contain Test since not a real test
class ReadmeGenerator {
    /**
     * This is not a test but a tool to generate the list of Metrics for the readme file.
     */
    @SuppressWarnings("java:S2699") // no assertions since not a test
    @Test
    void printMetrics() {
        System.out.println(Arrays.stream(ExasolStatisticsTableMetric.values())
                .map(each -> "[`" + each.name()
                        + "`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#"
                        + each.getTable().name() + ")")
                .collect(Collectors.joining(", ")));
    }
}
