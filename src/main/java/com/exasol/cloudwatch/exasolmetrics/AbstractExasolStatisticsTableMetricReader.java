package com.exasol.cloudwatch.exasolmetrics;

import java.sql.*;
import java.util.Calendar;
import java.util.TimeZone;

import com.exasol.errorreporting.ExaError;

/**
 * This class is an abstract basis for {@link ExasolMetricReader} that reads metrics from the {@code EXA_STATISTICS}
 * tables.
 */
abstract class AbstractExasolStatisticsTableMetricReader implements ExasolMetricReader {
    protected static final TimeZone UTC_ZONE = TimeZone.getTimeZone("UTC");
    protected final Connection connection;
    protected final Calendar utcCalendar;
    private final String schemaOverride;

    /**
     * Create a new instance of {@link AbstractExasolStatisticsTableMetricReader}.
     *
     * @param connection     connection to the exasol database
     * @param schemaOverride if null EXA_STATISTICS is used. This parameter allows you to test this connector with a
     *                       predefined SCHEMA instead of the unmodifiable live statistics.
     */
    protected AbstractExasolStatisticsTableMetricReader(final Connection connection, final String schemaOverride) {
        this.connection = connection;
        this.schemaOverride = schemaOverride;
        this.utcCalendar = Calendar.getInstance(UTC_ZONE);
        setSessionTimezoneToUtc();
    }

    private void setSessionTimezoneToUtc() {
        try (final Statement statement = this.connection.createStatement()) {
            statement.executeUpdate("ALTER SESSION SET TIME_ZONE = 'UTC'");
        } catch (final SQLException exception) {
            throw new IllegalStateException(ExaError.messageBuilder("F-CWA-13")
                    .message("Failed to change the Exasol session time zone to UTC.").ticketMitigation().toString(),
                    exception);
        }
    }

    /**
     * Get the injection free schema name. Typically this is {@code EXA_STATISTICS}, it is different only when
     * {@link #schemaOverride} is set in the constructor.
     * 
     * @return schema name
     */
    protected String getSchema() {
        if (this.schemaOverride == null) {
            return "EXA_STATISTICS";
        } else {
            return this.schemaOverride.replace("\"", "");
        }
    }
}
