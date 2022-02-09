package com.exasol.cloudwatch.exasolmetrics;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Factory for {@link ExasolStatisticsTableEventsMetricReader}.
 */
class ExasolStatisticsTableEventsMetricReaderFactory implements ExasolMetricReaderFactory {

    @Override
    public Set<String> getSupportedMetrics() {
        return Arrays.stream(ExasolStatisticsTableEventsMetric.values()).map(Enum::name).collect(Collectors.toSet());
    }

    @Override
    public ExasolMetricReader getReader(final Connection connection, final String schemaOverride) {
        return new ExasolStatisticsTableEventsMetricReader(connection, schemaOverride);
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
