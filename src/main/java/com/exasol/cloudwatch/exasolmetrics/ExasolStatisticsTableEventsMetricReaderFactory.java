package com.exasol.cloudwatch.exasolmetrics;

import java.sql.Connection;
import java.util.*;
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
        return Arrays.stream(ExasolStatisticsTableRegularMetric.values())//
                .filter(eachMetric -> eachMetric.name().equals(metric))//
                .map(eachMetric -> "https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#"
                        + eachMetric.getTable())
                .findAny();
    }
}
