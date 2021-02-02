package com.exasol.cloudwatch;

import static com.exasol.cloudwatch.ExasolStatisticsTable.*;
import static com.exasol.cloudwatch.ExasolUnit.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.exasol.errorreporting.ExaError;

/**
 * Enum with the relevant columns of the EXA_STATISTICS tables.
 */
public enum ExasolStatisticsTableMetric {

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
    USERS(EXA_USAGE_LAST_DAY, COUNT), //
    QUERIES(EXA_USAGE_LAST_DAY, COUNT);

    private final ExasolStatisticsTable table;
    private final ExasolUnit unit;

    /**
     * Create a new instance of {@link ExasolStatisticsTableMetric}.
     * 
     * @param tableName name of the table that contains the metric
     * @param unit      unit of this metric
     */
    private ExasolStatisticsTableMetric(final ExasolStatisticsTable tableName, final ExasolUnit unit) {
        this.table = tableName;
        this.unit = unit;
    }

    /**
     * Build a {@link ExasolStatisticsTableMetric} from string.
     * <p>
     * In comparison to {@link #valueOf(String)} this method has better error messages.
     * </p>
     *
     * @param name name of the metric
     * @return built {@link ExasolStatisticsTableMetric}
     */
    public static ExasolStatisticsTableMetric parse(final String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException exception) {
            final List<String> availableMetrics = Arrays.stream(ExasolStatisticsTableMetric.values()).map(Enum::name)
                    .collect(Collectors.toList());
            throw new IllegalArgumentException(ExaError.messageBuilder("E-CWA-9")
                    .message("Unknown metric {{unknown metric}}.")
                    .mitigation("Supported metrics are {{available metrics}}.").parameter("unknown metric", name)
                    .parameter("available metrics", availableMetrics).toString());
        }
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
     * Get the unit of this metric.
     *
     * @return unit of this metric
     */
    public ExasolUnit getUnit() {
        return this.unit;
    }
}
