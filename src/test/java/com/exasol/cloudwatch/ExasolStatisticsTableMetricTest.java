package com.exasol.cloudwatch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ExasolStatisticsTableMetricTest {

    @Test
    void testParseUnknown() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ExasolStatisticsTableMetric.parse("UNKNOWN"));
        assertThat(exception.getMessage(), startsWith("E-CWA-9: Unknown metric 'UNKNOWN'. Supported metrics are ['"));
    }
}
