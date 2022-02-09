package com.exasol.cloudwatch.exasolmetrics;

/**
 * This enum contains all supported DB events reported in the <a href=
 * "https://docs.exasol.com/saas/sql_references/system_tables/statistical/exa_system_events.htm">EXA_SYSTEM_EVENTS</a>
 * table.
 */
public enum ExasolEvent {
    /** Backup has started */
    BACKUP_START("BACKUP_START", "EVENT_BACKUP_START"),
    /** Backup has finished successfully */
    BACKUP_END("BACKUP_END", "EVENT_BACKUP_END"),
    /** Backup was aborted manually or failed */
    BACKUP_ABORTED("BACKUP_ABORTED", "EVENT_BACKUP_ABORTED");

    private final String dbEventType;
    private final String metricsName;

    private ExasolEvent(String dbEventType, String metricsName) {
        this.metricsName = metricsName;
        this.dbEventType = dbEventType;
    }

    /**
     * The event type in the {@code EVENT_TYPE} column of the <a href=
     * "https://docs.exasol.com/saas/sql_references/system_tables/statistical/exa_system_events.htm">EXA_SYSTEM_EVENTS</a>
     * table.
     * 
     * @return the event type used by Exasol
     */
    public String getDbEventType() {
        return dbEventType;
    }

    /**
     * The metrics name specified by the user.
     * 
     * @return the metrics name
     */
    public String getMetricsName() {
        return metricsName;
    }
}
