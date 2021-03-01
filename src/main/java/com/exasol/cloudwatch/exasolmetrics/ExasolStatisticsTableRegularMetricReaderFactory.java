package com.exasol.cloudwatch.exasolmetrics;

import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Factory for {@link ExasolStatisticsTableRegularMetricReader}.
 */
class ExasolStatisticsTableRegularMetricReaderFactory implements ExasolMetricReaderFactory {

    @Override
    public Set<String> getSupportedMetrics() {
        return Arrays.stream(ExasolStatisticsTableRegularMetric.values()).map(Enum::name).collect(Collectors.toSet());
    }

    @Override
    public ExasolMetricReader getReader(final Connection connection, final String schemaOverride) {
        return new ExasolStatisticsTableRegularMetricReader(connection, schemaOverride);
    }

    @Override
    public Optional<String> getDocLinkForMetric(final String metric) {
        return Optional
                .of("https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_SYSTEM_EVENTS");
    }
}
