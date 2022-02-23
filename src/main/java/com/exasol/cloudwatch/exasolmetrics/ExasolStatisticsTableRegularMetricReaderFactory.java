package com.exasol.cloudwatch.exasolmetrics;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
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
        return Arrays.stream(ExasolStatisticsTableRegularMetric.values())//
                .filter(eachMetric -> eachMetric.name().equals(metric))//
                .map(eachMetric -> "https://docs.exasol.com/db/latest/sql_references/system_tables/statistical/"
                        + eachMetric.getTable().name().toLowerCase() + ".htm")
                .findAny();
    }
}
