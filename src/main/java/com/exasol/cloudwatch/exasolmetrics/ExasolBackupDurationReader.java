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
 * This {@link ExasolEventMetricsReader} reads the duration of the finished within the last minute.
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
        return "with intermediate as ( " //
                + "    select s.cluster_name, s.measure_time, s.event_type, " //
                + "        lead(event_type) over (partition by cluster_name order by measure_time) end_event, " //
                + "        lead(measure_time) over (partition by cluster_name order by measure_time) end_time " //
                + "    from \"" + getSchema() + "\".exa_system_events s " //
                + "    where event_type like 'BACKUP%' " //
                + ") " //
                + "select i.cluster_name, " //
                + "    CONVERT_TZ(i.end_time, DBTIMEZONE, 'UTC', 'INVALID REJECT AMBIGUOUS REJECT') as UTC_END_TIME, " //
                + "    cast(seconds_between(end_time, measure_time) as decimal(10, 2)) backup_duration_sec " //
                + "from intermediate i " //
                + "where event_type = 'BACKUP_START' " //
                + "    and end_event not like '%START' " //
                + "    and i.end_time >= CONVERT_TZ(?, 'UTC', DBTIMEZONE, 'INVALID REJECT AMBIGUOUS REJECT') " //
                + "    and i.end_time < CONVERT_TZ(?, 'UTC', DBTIMEZONE, 'INVALID REJECT AMBIGUOUS REJECT') " //
                + "order by measure_time desc;";
    }

    private IllegalStateException wrapSqlException(final String query, final SQLException exception) {
        return new IllegalStateException(ExaError.messageBuilder("F-CWA-35")
                .message("Failed to execute query ({{query}}) on system table.", query).ticketMitigation().toString(),
                exception);
    }
}
