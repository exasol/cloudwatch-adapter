package com.exasol.cloudwatch.exasolmetrics;

import static com.exasol.cloudwatch.exasolmetrics.ExasolUnit.COUNT;
import static com.exasol.cloudwatch.exasolmetrics.ExasolUnit.GIBIBYTES;

/**
 * Enum with the relevant columns of the {@code EXA_STATISTIC.EXA_SYSTEM_EVENTS} table. This table needs a different
 * reader than that of other tables, since Exasol does not report these values periodically but, for example, after
 * reboot.
 */
enum ExasolStatisticsTableEventsMetric {
    NODES(COUNT, 4), //
    DB_RAM_SIZE(GIBIBYTES, 4), //
    VCPU(COUNT, 4);

    private final ExasolUnit unit;
    private final int reportIntervalMinutes;

    /**
     * Create a new instance of {@link ExasolStatisticsTableEventsMetric}.
     *
     * @param unit                  unit of this metric
     * @param reportIntervalMinutes interval how often the metric should be reported
     */
    private ExasolStatisticsTableEventsMetric(final ExasolUnit unit, final int reportIntervalMinutes) {
        this.unit = unit;
        this.reportIntervalMinutes = reportIntervalMinutes;
    }

    /**
     * Get the unit.
     * 
     * @return unit
     */
    public ExasolUnit getUnit() {
        return this.unit;
    }

    /**
     * Get the interval in that the metric should be reported.
     * 
     * @return interval in minutes
     */
    public int getReportIntervalMinutes() {
        return this.reportIntervalMinutes;
    }
}
