package com.exasol.cloudwatch.exasolmetrics;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class CompoundExasolMetricReaderTest {

    @Test
    void testUnknownMetric() {
        final ExasolMetricReaderFactory metricReaderFactory = mock(ExasolMetricReaderFactory.class);
        when(metricReaderFactory.getSupportedMetrics()).thenAnswer(invocation -> Set.of("MY_METRIC"));
        final CompoundExasolMetricReader reader = new CompoundExasolMetricReader(List.of(metricReaderFactory), null,
                null);
        final List<String> requestMetrics = List.of("UNKNOWN");
        final Instant now = Instant.now();
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reader.readMetrics(requestMetrics, now));
        assertAll(//
                () -> assertThat(exception.getMessage(), startsWith("E-CWA-31")),
                () -> assertThat(exception.getMessage(), containsString("UNKNOWN")),
                () -> assertThat(exception.getMessage(), containsString("MY_METRIC"))//
        );
    }
}