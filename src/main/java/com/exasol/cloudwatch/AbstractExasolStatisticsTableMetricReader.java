package com.exasol.cloudwatch;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.exasol.errorreporting.ExaError;

/**
 * This class is an abstract basis for {@link ExasolMetricReader} that reads metrics from the {@code EXA_STATISTICS} tables.
 */
public abstract class AbstractExasolStatisticsTableMetricReader implements ExasolMetricReader {
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

    // [impl->dsn~report-minute-before-event~1]
    @Override
    public List<ExasolMetricDatum> readMetrics(final List<ExasolStatisticsTableMetric> metrics,
            final Instant ofMinute) {
        final Instant start = ofMinute.truncatedTo(ChronoUnit.MINUTES);
        final Instant end = start.plus(Duration.ofMinutes(1));
        assertIntervalIsInThePastForTheDatabaseTime(end);
        final Stream<ExasolStatisticsTable> requestedTables = metrics.stream()
                .map(ExasolStatisticsTableMetric::getTable).distinct();
        return requestedTables.flatMap(table -> loadMetricsForTable(metrics, start, end, table).stream())
                .collect(Collectors.toList());
    }

    /**
     * Read the metrics from the EXA_STATISTICS table.
     * 
     * @param metrics         list of metrics to read.
     * @param start           start of the timeframe
     * @param end             end of the timeframe
     * @param statisticsTable EXA_STATISTICS table
     * @return read metrics
     */
    protected abstract List<ExasolMetricDatum> loadMetricsForTable(final List<ExasolStatisticsTableMetric> metrics,
            final Instant start, final Instant end, final ExasolStatisticsTable statisticsTable);

    private void assertIntervalIsInThePastForTheDatabaseTime(final Instant end) {
        final Instant databaseTime = getDatabaseTime();
        if (end.isAfter(databaseTime)) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-CWA-11").message(
                    "The current date of the database ({{db date}}) is before the end of the interval ({{end}}) of this report.")
                    .mitigation("Check your clock Exasol databases clock synchronization.")
                    .parameter("db date", databaseTime).parameter("end", end).toString());
        }
    }

    private Instant getDatabaseTime() {
        try (final Statement statement = this.connection.createStatement();
                final ResultSet resultSet = statement.executeQuery("SELECT NOW()")) {
            resultSet.next();
            return resultSet.getTimestamp(1, this.utcCalendar).toInstant();
        } catch (final SQLException exception) {
            throw new IllegalStateException(ExaError.messageBuilder("F-CWA-10")
                    .message("Failed to get current time of exasol database.").ticketMitigation().toString(),
                    exception);
        }
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
     * Get the injection free schema name. Typically this is {@code EXA_STATISTICS}. Only if in the constructor
     * {@link #schemaOverride} was set this schema is returned.
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
