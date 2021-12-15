package com.exasol.cloudwatch;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.exasol.cloudwatch.configuration.*;
import com.exasol.cloudwatch.exasolmetrics.*;
import com.exasol.errorreporting.ExaError;

import software.amazon.awssdk.services.cloudwatch.model.MetricDatum;

/**
 * This class is the entry point for the Lambda function of the cloud watch adapter.
 */
public class CloudWatchAdapter implements RequestHandler<ScheduledEvent, Void> {

    private static final Logger logger = LoggerFactory.getLogger(CloudWatchAdapter.class);
    private final String exasolStatisticsSchemaOverride;
    private final AdapterConfiguration configuration;
    private final AwsClientFactory awsClientFactory;
    private final ExasolMetricProvider exasolMetricProvider;

    /**
     * Create a new instance of {@link CloudWatchAdapter}.
     */
    public CloudWatchAdapter() {
        this(null, new DefaultAwsClientFactory(), EnvironmentVariableProvider.getDefault());
    }

    /**
     * Constructor for testing.
     *
     * @param exasolStatisticsSchemaOverride if null EXA_STATISITCS is used. This parameter allows you to test this
     *                                       connector with a * predefined SCHEMA instead of the unmodifiable live
     *                                       statistics.
     * @param awsClientFactory               dependency injection of AWS clients
     * @param environmentVariableProvider    dependency injection of the {@link EnvironmentConfigurationReader}
     */
    CloudWatchAdapter(final String exasolStatisticsSchemaOverride, final AwsClientFactory awsClientFactory,
            final EnvironmentVariableProvider environmentVariableProvider) {
        this.awsClientFactory = awsClientFactory;
        this.exasolMetricProvider = new ExasolMetricProvider();
        this.configuration = new AdapterConfigurationReader(this.awsClientFactory, environmentVariableProvider)
                .readConfiguration(this.exasolMetricProvider.getSupportedMetrics());
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
        final ExasolMetricReader reader = this.exasolMetricProvider.getReader(exasolConnection,
                this.exasolStatisticsSchemaOverride);
        final ExasolToCloudwatchMetricDatumConverter converter = new ExasolToCloudwatchMetricDatumConverter(
                this.configuration.getDeploymentName());
        final CloudWatchPointWriter pointWriter = new CloudWatchPointWriter(this.awsClientFactory);
        final List<ExasolMetricDatum> exasolMetricData = reader.readMetrics(this.configuration.getEnabledMetrics(),
                minuteToReport);
        final List<MetricDatum> cloudwatchMetricData = exasolMetricData.stream().map(converter::convert)
                .collect(Collectors.toList());
        logger.info("Writing {} points.", cloudwatchMetricData.size());
        pointWriter.putPointsInChunks(cloudwatchMetricData);
    }

    private Connection connectToExasol() {
        try {
            final ExasolCredentials exasolCredentials = this.configuration.getExasolCredentials();
            return DriverManager.getConnection(getJdbcUrl(exasolCredentials), exasolCredentials.getUser(),
                    exasolCredentials.getPass());
        } catch (final SQLException exception) {
            throw new IllegalStateException(
                    ExaError.messageBuilder("E-CWA-2").message("Failed to connect to the Exasol database.").toString(),
                    exception);
        }
    }

    private String getJdbcUrl(final ExasolCredentials exasolCredentials) {
        final String url = "jdbc:exa:" + exasolCredentials.getHost() + ":" + exasolCredentials.getPort()
                + ";schema=SYS";
        final String fingerprint = exasolCredentials.getCertificateFingerprint();
        if ((fingerprint == null) || fingerprint.isBlank()) {
            return url;
        } else {
            return url + ";fingerprint=" + fingerprint;
        }
    }
}
