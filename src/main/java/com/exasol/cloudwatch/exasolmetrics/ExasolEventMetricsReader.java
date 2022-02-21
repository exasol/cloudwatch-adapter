package com.exasol.cloudwatch.exasolmetrics;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toMap;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exasol.errorreporting.ExaError;

/**
 * This {@link ExasolMetricReader} reads events that occured within the last minute.
 */
class ExasolEventMetricsReader extends AbstractExasolStatisticsTableMetricReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExasolEventMetricsReader.class);

    /**
     * Create a new instance of {@link ExasolEventMetricsReader}.
     *
     * @param connection     connection to the Exasol database
     * @param schemaOverride if null {@code EXA_STATISTICS} is used. This parameter allows you to test this connector
     *                       with a predefined SCHEMA instead of the unmodifiable live statistics.
     */
    ExasolEventMetricsReader(final Connection connection, final String schemaOverride) {
        super(connection, schemaOverride);
    }

    @Override
    public List<ExasolMetricDatum> readMetrics(final Collection<String> metricsNames, final Instant ofMinute) {
        final List<ExasolEvent> metrics = metricsNames.stream().map(ExasolEvent::forName).collect(Collectors.toList());
        if (metrics.isEmpty()) {
            return emptyList();
        }
        final Instant start = ofMinute.truncatedTo(ChronoUnit.MINUTES);
        final Instant end = start.plus(Duration.ofMinutes(1));

        final String query = buildQuery();
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            LOGGER.debug("Query events between {} and {}", start, end);
            statement.setTimestamp(1, Timestamp.from(start), this.utcCalendar);
            statement.setTimestamp(2, Timestamp.from(end), this.utcCalendar);
            return executeSystemTableQuery(metrics, statement);
        } catch (final SQLException exception) {
            if (exception.getMessage().contains("ambigous timestamp")) {
                LOGGER.warn(ExaError.messageBuilder("W-CWA-21").message("Skipping points due to timeshift. ").message(
                        "Since the Exasol database stores the logs with dates in the DBTIMEZONE there are ambiguous logs during the timeshift.")
                        .mitigation("The only thing you can do is to change your DBTIMEZONE to UTC.").toString());
                return List.of();
            } else {
                throw wrapSqlException(query, exception);
            }
        }
    }

    private List<ExasolMetricDatum> executeSystemTableQuery(final List<ExasolEvent> metrics,
            final PreparedStatement statement) throws SQLException {
        final List<ExasolMetricDatum> result = new ArrayList<>();
        final Map<String, ExasolEvent> wantedEventTypes = metrics.stream()
                .collect(toMap(ExasolEvent::getDbEventType, Function.identity()));
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                final String eventType = resultSet.getString("EVENT_TYPE");
                final ExasolEvent event = wantedEventTypes.get(eventType);
                if (event == null) {
                    continue;
                }
                final String clusterName = resultSet.getString("CLUSTER_NAME");
                final Instant timestamp = resultSet.getTimestamp("UTC_MEASURE_TIME", this.utcCalendar).toInstant();
                result.add(new ExasolMetricDatum(event.getMetricsName(), ExasolUnit.COUNT, timestamp, 1, clusterName));
            }
        }
        return result;
    }

    private String buildQuery() {
        return "SELECT "
                + "CONVERT_TZ(MEASURE_TIME, DBTIMEZONE, 'UTC', 'INVALID REJECT AMBIGUOUS REJECT') as UTC_MEASURE_TIME, t.CLUSTER_NAME, t.EVENT_TYPE "
                + "FROM \"" + getSchema() + "\".\"" + ExasolStatisticsTable.EXA_SYSTEM_EVENTS.name() + "\"" + " t "
                + "WHERE MEASURE_TIME >= CONVERT_TZ(?, 'UTC', DBTIMEZONE, 'INVALID REJECT AMBIGUOUS REJECT') "
                + "AND MEASURE_TIME < CONVERT_TZ(?, 'UTC', DBTIMEZONE, 'INVALID REJECT AMBIGUOUS REJECT')";
    }

    private IllegalStateException wrapSqlException(final String query, final SQLException exception) {
        return new IllegalStateException(ExaError.messageBuilder("F-CWA-22")
                .message("Failed to execute query ({{query}}) on system table.", query).ticketMitigation().toString(),
                exception);
    }
}
