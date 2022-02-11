package com.exasol.cloudwatch.exasolmetrics;

import java.sql.Connection;
import java.util.Optional;
import java.util.Set;

/**
 * Factory for {@link ExasolBackupDurationReader}.
 */
class ExasolBackupDurationReaderFactory implements ExasolMetricReaderFactory {

    static final String METRIC_NAME = "BACKUP_DURATION";

    @Override
    public Set<String> getSupportedMetrics() {
        return Set.of(METRIC_NAME);
    }

    @Override
    public ExasolMetricReader getReader(final Connection connection, final String schemaOverride) {
        return new ExasolBackupDurationReader(connection, schemaOverride);
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
