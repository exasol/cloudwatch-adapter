package com.exasol.cloudwatch.exasolmetrics;

import static java.util.stream.Collectors.toSet;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

class ExasolEventMetricsReaderFactory implements ExasolMetricReaderFactory {

    @Override
    public Set<String> getSupportedMetrics() {
        return Arrays.stream(ExasolEvent.values()).map(ExasolEvent::getMetricsName).collect(toSet());
    }

    @Override
    public ExasolMetricReader getReader(Connection connection, String schemaOverride) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<String> getDocLinkForMetric(String metric) {
        if (getSupportedMetrics().contains(metric)) {
            return Optional.of(
                    "https://docs.exasol.com/db/latest/sql_references/system_tables/statistical/exa_system_events.htm");
        } else {
            return Optional.empty();
        }
    }
}
