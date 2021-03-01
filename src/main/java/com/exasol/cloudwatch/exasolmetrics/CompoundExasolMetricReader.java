package com.exasol.cloudwatch.exasolmetrics;

import java.sql.Connection;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.exasol.errorreporting.ExaError;

/**
 * This class combines multiple {@link ExasolMetricReader}s into one.
 */
class CompoundExasolMetricReader implements ExasolMetricReader {

    private final List<ExasolMetricReaderFactory> childReaderFactories;
    private final Connection connection;
    private final String schemaOverride;

    /**
     * Create a new instance of {@link CompoundExasolMetricReader}.
     * 
     * @param childReaderFactories factories for the child readers to combine
     * @param connection           connection to the exasol database
     * @param schemaOverride       if null EXA_STATISTICS is used. This parameter allows you to test this connector with
     *                             a predefined SCHEMA instead of the unmodifiable live statistics.
     */
    public CompoundExasolMetricReader(final List<ExasolMetricReaderFactory> childReaderFactories,
            final Connection connection, final String schemaOverride) {
        this.childReaderFactories = childReaderFactories;
        this.connection = connection;
        this.schemaOverride = schemaOverride;
    }

    @Override
    public List<ExasolMetricDatum> readMetrics(final Collection<String> metrics, final Instant ofMinute) {
        final Set<String> remainingMetrics = new HashSet<>(metrics);
        final List<ExasolMetricDatum> result = this.childReaderFactories.stream().flatMap(readerFactory -> {
            final Set<String> thisReadersMetrics = getMetricsSupportedByReader(remainingMetrics, readerFactory);
            remainingMetrics.removeAll(thisReadersMetrics);
            if (thisReadersMetrics.isEmpty()) {
                return Stream.empty();
            } else {
                return readerFactory.getReader(this.connection, this.schemaOverride)
                        .readMetrics(thisReadersMetrics, ofMinute).stream();
            }
        }).collect(Collectors.toList());
        assertAllMetricsWereFound(remainingMetrics);
        return result;
    }

    private Set<String> getMetricsSupportedByReader(final Set<String> remainingMetrics,
            final ExasolMetricReaderFactory readerFactory) {
        final Set<String> thisReadersSupportedMetrics = readerFactory.getSupportedMetrics();
        return intersection(remainingMetrics, thisReadersSupportedMetrics);
    }

    private void assertAllMetricsWereFound(final Set<String> remainingMetrics) {
        final List<String> supportedMetrics = this.childReaderFactories.stream()
                .flatMap(factory -> factory.getSupportedMetrics().stream()).collect(Collectors.toList());
        if (!remainingMetrics.isEmpty()) {
            throw new IllegalArgumentException(
                    ExaError.messageBuilder("E-CWA-31").message("Invalid metrics {{invalid metrics}}.")
                            .mitigation("This adapter can report the following metrics: {{supported metrics}}.")
                            .parameter("invalid metrics", remainingMetrics)
                            .parameter("supported metrics", supportedMetrics).toString());
        }
    }

    private Set<String> intersection(final Set<String> setA, final Set<String> setB) {
        final Set<String> result = new HashSet<>(setB);
        result.retainAll(setA);
        return result;
    }
}
