package com.exasol.cloudwatch.exasolmetrics;

import static java.util.Collections.emptyList;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exasol.errorreporting.ExaError;

/**
 * This {@link ExasolEventMetricsReader} reads the duration of the backup finished within the last minute if available.
 */
class ExasolBackupDurationReader extends AbstractExasolStatisticsTableMetricReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExasolBackupDurationReader.class);

    /**
     * Create a new instance of {@link ExasolBackupDurationReader}.
     *
     * @param connection     connection to the Exasol database
     * @param schemaOverride if null {@code EXA_STATISTICS} is used. This parameter allows you to test this connector
     *                       with a predefined SCHEMA instead of the unmodifiable live statistics.
     */
    ExasolBackupDurationReader(final Connection connection, final String schemaOverride) {
        super(connection, schemaOverride);
    }

    @Override
    public List<ExasolMetricDatum> readMetrics(final Collection<String> metricsNames, final Instant ofMinute) {
        if (!metricsNames.contains(ExasolBackupDurationReaderFactory.METRIC_NAME)) {
            return emptyList();
        }
        final Instant start = ofMinute.truncatedTo(ChronoUnit.MINUTES);
        final Instant end = start.plus(Duration.ofMinutes(1));
        final String query = buildQuery();
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            LOGGER.debug("Query events between {} and {}", start, end);
            statement.setTimestamp(1, Timestamp.from(start), this.utcCalendar);
            statement.setTimestamp(2, Timestamp.from(end), this.utcCalendar);
            return executeQuery(statement);
        } catch (final SQLException exception) {
            if (exception.getMessage().contains("ambigous timestamp")) {
                LOGGER.warn(ExaError.messageBuilder("W-CWA-34").message("Skipping points due to timeshift. ").message(
                        "Since the Exasol database stores the logs with dates in the DBTIMEZONE there are ambiguous logs during the timeshift.")
                        .mitigation("The only thing you can do is to change your DBTIMEZONE to UTC.").toString());
                return List.of();
            } else {
                throw wrapSqlException(query, exception);
            }
        }
    }

    private List<ExasolMetricDatum> executeQuery(final PreparedStatement statement) throws SQLException {
        final List<ExasolMetricDatum> result = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                final String clusterName = resultSet.getString("CLUSTER_NAME");
                final Instant timestamp = resultSet.getTimestamp("UTC_END_TIME", this.utcCalendar).toInstant();
                final double backupDurationSeconds = resultSet.getDouble("backup_duration_sec");
                result.add(new ExasolMetricDatum(ExasolBackupDurationReaderFactory.METRIC_NAME, ExasolUnit.SECONDS,
                        timestamp, backupDurationSeconds, clusterName));
            }
        }
        return result;
    }

    private String buildQuery() {
        return "WITH INTERMEDIATE AS ( " //
                + "    SELECT s.cluster_name, s.measure_time, s.event_type, " //
                + "        LEAD(event_type, 1) OVER (PARTITION BY cluster_name ORDER BY measure_time) next_event_event, " //
                + "        LEAD(measure_time, 1) OVER (PARTITION BY cluster_name ORDER BY measure_time) next_event_time " //
                + "    FROM \"" + getSchema() + "\".exa_system_events s " //
                + "    WHERE event_type LIKE 'BACKUP%' " //
                + ") " //
                + "SELECT cluster_name, " //
                + "    CONVERT_TZ(next_event_time, DBTIMEZONE, 'UTC', 'INVALID REJECT AMBIGUOUS REJECT') AS UTC_END_TIME, " //
                + "    CAST(SECONDS_BETWEEN(next_event_time, measure_time) AS DECIMAL(10, 2)) backup_duration_sec " //
                + "FROM INTERMEDIATE eventsWithNextEvent " //
                + "WHERE event_type = 'BACKUP_START' " //
                + "    AND next_event_event NOT LIKE '%START' " //
                + "    AND next_event_time >= CONVERT_TZ(?, 'UTC', DBTIMEZONE, 'INVALID REJECT AMBIGUOUS REJECT') " //
                + "    AND next_event_time < CONVERT_TZ(?, 'UTC', DBTIMEZONE, 'INVALID REJECT AMBIGUOUS REJECT') " //
                + "ORDER BY measure_time DESC;";
    }

    private IllegalStateException wrapSqlException(final String query, final SQLException exception) {
        return new IllegalStateException(ExaError.messageBuilder("F-CWA-35")
                .message("Failed to execute query ({{query}}) on system table.", query).ticketMitigation().toString(),
                exception);
    }
}
