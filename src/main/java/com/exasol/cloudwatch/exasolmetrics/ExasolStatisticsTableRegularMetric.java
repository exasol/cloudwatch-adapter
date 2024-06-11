package com.exasol.cloudwatch.exasolmetrics;

import static com.exasol.cloudwatch.exasolmetrics.ExasolStatisticsTable.*;
import static com.exasol.cloudwatch.exasolmetrics.ExasolUnit.*;

/**
 * Enum with the relevant columns of the EXA_STATISTICS tables.
 */
enum ExasolStatisticsTableRegularMetric {

    RAW_OBJECT_SIZE(EXA_DB_SIZE_LAST_DAY, GIBIBYTES), //
    MEM_OBJECT_SIZE(EXA_DB_SIZE_LAST_DAY, GIBIBYTES), //
    AUXILIARY_SIZE(EXA_DB_SIZE_LAST_DAY, GIBIBYTES), //
    STATISTICS_SIZE(EXA_DB_SIZE_LAST_DAY, GIBIBYTES), //
    RECOMMENDED_DB_RAM_SIZE(EXA_DB_SIZE_LAST_DAY, GIBIBYTES), //
    STORAGE_SIZE(EXA_DB_SIZE_LAST_DAY, GIBIBYTES), //
    USE(EXA_DB_SIZE_LAST_DAY, PERCENT), //
    TEMP_SIZE(EXA_DB_SIZE_LAST_DAY, GIBIBYTES), //
    OBJECT_COUNT(EXA_DB_SIZE_LAST_DAY, COUNT), //
    LOAD(EXA_MONITOR_LAST_DAY, NONE), //
    CPU(EXA_MONITOR_LAST_DAY, PERCENT), //
    TEMP_DB_RAM(EXA_MONITOR_LAST_DAY, MEBIBYTES), //
    PERSISTENT_DB_RAM(EXA_MONITOR_LAST_DAY, MEBIBYTES), //
    HDD_READ(EXA_MONITOR_LAST_DAY, MEBIBYTES_PER_SECOND), //
    HDD_WRITE(EXA_MONITOR_LAST_DAY, MEBIBYTES_PER_SECOND), //
    LOCAL_READ_SIZE(EXA_MONITOR_LAST_DAY, MEBIBYTES), //
    LOCAL_READ_DURATION(EXA_MONITOR_LAST_DAY, SECONDS), //
    LOCAL_WRITE_DURATION(EXA_MONITOR_LAST_DAY, SECONDS), //
    CACHE_READ_SIZE(EXA_MONITOR_LAST_DAY, MEBIBYTES), //
    CACHE_READ_DURATION(EXA_MONITOR_LAST_DAY, SECONDS), //
    CACHE_WRITE_SIZE(EXA_MONITOR_LAST_DAY, MEBIBYTES), //
    CACHE_WRITE_DURATION(EXA_MONITOR_LAST_DAY, SECONDS), //
    REMOTE_READ_SIZE(EXA_MONITOR_LAST_DAY, MEBIBYTES), //
    REMOTE_READ_DURATION(EXA_MONITOR_LAST_DAY, SECONDS), //
    REMOTE_WRITE_SIZE(EXA_MONITOR_LAST_DAY, MEBIBYTES), //
    REMOTE_WRITE_DURATION(EXA_MONITOR_LAST_DAY, SECONDS), //
    NET(EXA_MONITOR_LAST_DAY, MEBIBYTES_PER_SECOND), //
    SWAP(EXA_MONITOR_LAST_DAY, MEBIBYTES_PER_SECOND), //
    USERS(EXA_USAGE_LAST_DAY, COUNT), //
    QUERIES(EXA_USAGE_LAST_DAY, COUNT), //
    NODES(EXA_SYSTEM_EVENTS, COUNT), //
    DB_RAM_SIZE(EXA_SYSTEM_EVENTS, GIBIBYTES), //
    VCPU(EXA_SYSTEM_EVENTS, COUNT);

    private final ExasolStatisticsTable table;
    private final ExasolUnit unit;

    /**
     * Create a new instance of {@link ExasolStatisticsTableRegularMetric}.
     * 
     * @param tableName name of the table that contains the metric
     * @param unit      unit of this metric
     */
    private ExasolStatisticsTableRegularMetric(final ExasolStatisticsTable tableName, final ExasolUnit unit) {
        this.table = tableName;
        this.unit = unit;
    }

    /**
     * Get the table that contains this metric.
     *
     * @return name of the table
     */
    public ExasolStatisticsTable getTable() {
        return this.table;
    }

    /**
     * Get the unit
     * 
     * @return unit
     */
    public ExasolUnit getUnit() {
        return this.unit;
    }
}
