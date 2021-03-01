package com.exasol.cloudwatch.exasolmetrics;

import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class combines multiple {@link ExasolMetricReaderFactory}s into one.
 */
class CompoundExasolMetricReaderFactory implements ExasolMetricReaderFactory {

    private final List<ExasolMetricReaderFactory> childReaderFactories;

    /**
     * Create a new instance of {@link CompoundExasolMetricReaderFactory}.
     * 
     * @param childReaderFactories childReaderFactories to combine
     */
    public CompoundExasolMetricReaderFactory(final List<ExasolMetricReaderFactory> childReaderFactories) {
        this.childReaderFactories = childReaderFactories;
    }

    @Override
    public Set<String> getSupportedMetrics() {
        return this.childReaderFactories.stream().flatMap(reader -> reader.getSupportedMetrics().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public ExasolMetricReader getReader(final Connection connection, final String schemaOverride) {
        return new CompoundExasolMetricReader(this.childReaderFactories, connection, schemaOverride);
    }

    @Override
    public Optional<String> getDocLinkForMetric(final String metric) {
        return this.childReaderFactories.stream().map(reader -> reader.getDocLinkForMetric(metric))
                .filter(Optional::isPresent).map(Optional::get).findAny();
    }
}
