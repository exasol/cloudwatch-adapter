package com.exasol.cloudwatch.exasolmetrics;

import static java.util.stream.Collectors.toSet;

import java.sql.Connection;
import java.util.*;

/**
 * Factory for {@link ExasolEventMetricsReader}.
 */
class ExasolEventMetricsReaderFactory implements ExasolMetricReaderFactory {

    @Override
    public Set<String> getSupportedMetrics() {
        return Arrays.stream(ExasolEvent.values()).map(ExasolEvent::getMetricsName).collect(toSet());
    }

    @Override
    public ExasolMetricReader getReader(final Connection connection, final String schemaOverride) {
        return new ExasolEventMetricsReader(connection, schemaOverride);
    }

    @Override
    public Optional<String> getDocLinkForMetric(final String metric) {
        if (getSupportedMetrics().contains(metric)) {
            return Optional.of(
                    "https://docs.exasol.com/db/latest/sql_references/system_tables/statistical/exa_system_events.htm");
        } else {
            return Optional.empty();
        }
    }
}
