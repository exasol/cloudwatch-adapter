package com.exasol.cloudwatch;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.exasol.errorreporting.ExaError;

/**
 * This class is the entry point for the Lambda function of the cloud watch adapter.
 */
public class CloudWatchAdapter implements RequestHandler<ScheduledEvent, Void> {

    private static final Logger logger = LoggerFactory.getLogger(CloudWatchAdapter.class);
    private final String exasolStatisticsSchemaOverride;
    private final AdapterConfiguration configuration;

    /**
     * Create a new instance of {@link CloudWatchAdapter}.
     */
    public CloudWatchAdapter() {
        this(null);
    }

    /**
     * Constructor for testing.
     * 
     * @param exasolStatisticsSchemaOverride if null EXA_STATISITCS is used. This parameter allows you to test this
     *                                       connector with a * predefined SCHEMA instead of the unmodifiable live
     *                                       statistics.
     */
    CloudWatchAdapter(final String exasolStatisticsSchemaOverride) {
        this.configuration = new AdapterConfiguration();
        this.exasolStatisticsSchemaOverride = exasolStatisticsSchemaOverride;
    }

    @Override
    public Void handleRequest(final ScheduledEvent event, final Context context) {
        final Instant eventTime = Instant.ofEpochMilli(event.getTime().getMillis());
        // [impl->dsn~report-minute-before-event~1]
        final Instant minuteToReport = eventTime.minus(Duration.ofMinutes(1));
        try (final Connection exasolConnection = connectToExasol()) {
            runSynchronization(minuteToReport, exasolConnection);
        } catch (final SQLException exception) {
            throw new IllegalStateException(ExaError.messageBuilder("E-CWA-6")
                    .message("Failed to close connection to the Exasol database.").toString(), exception);
        }
        return null;
    }

    private void runSynchronization(final Instant minuteToReport, final Connection exasolConnection) {
        final ExasolStatisticsTableMetricReader metricReader = new ExasolStatisticsTableMetricReader(exasolConnection,
                this.exasolStatisticsSchemaOverride);
        final CloudWatchPointWriter pointWriter = new CloudWatchPointWriter(this.configuration.getDeploymentName());
        final List<SystemTableDataPoint> systemTableDataPoints = metricReader
                .readMetrics(this.configuration.getEnabledMetrics(), minuteToReport);
        logger.info("Writing {} points.", systemTableDataPoints.size());
        pointWriter.putPointsInChunks(systemTableDataPoints);
    }

    private Connection connectToExasol() {
        try {
            return DriverManager
                    .getConnection(
                            "jdbc:exa:" + this.configuration.getExasolHost() + ":" + this.configuration.getExasolPort()
                                    + ";schema=SYS",
                            this.configuration.getExasolUser(), this.configuration.getExasolPass());
        } catch (final SQLException exception) {
            throw new IllegalStateException(
                    ExaError.messageBuilder("E-CWA-2").message("Failed to connect to the Exasol database.").toString(),
                    exception);
        }
    }
}
