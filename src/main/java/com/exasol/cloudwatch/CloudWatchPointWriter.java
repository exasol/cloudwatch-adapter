package com.exasol.cloudwatch;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.MetricDatum;
import software.amazon.awssdk.services.cloudwatch.model.PutMetricDataRequest;

/**
 * This class writes measurement point to AWS cloud watch using the AWS SDK v2.
 */
public class CloudWatchPointWriter {
    public static final String CLOUDWATCH_NAMESPACE = "Exasol";
    private static final int CHUNK_SIZE = 20;
    private final AwsClientFactory awsClientFactory;

    /**
     * Create a new instance of {@link CloudWatchPointWriter}.
     *
     * @param awsClientConfigurator callback that allows tests to use alternate cloudwatch configuration
     */
    public CloudWatchPointWriter(final AwsClientFactory awsClientConfigurator) {
        this.awsClientFactory = awsClientConfigurator;
    }

    /**
     * Write a list of points to AWS CloudWatch.
     * 
     * @param exasolStatisticsTableMetricData points to write
     */
    public void putPointsInChunks(final List<MetricDatum> exasolStatisticsTableMetricData) {
        try (final CloudWatchClient cloudwatch = this.awsClientFactory.getCloudWatchClient()) {
            for (int chunkCounter = 0; chunkCounter * CHUNK_SIZE < exasolStatisticsTableMetricData
                    .size(); chunkCounter++) {
                final Stream<MetricDatum> chuck = exasolStatisticsTableMetricData.stream()
                        .skip((long) chunkCounter * CHUNK_SIZE).limit(CHUNK_SIZE);
                putPoints(cloudwatch, chuck);
            }
        }
    }

    private void putPoints(final CloudWatchClient cloudwatch, final Stream<MetricDatum> points) {
        final PutMetricDataRequest putRequest = PutMetricDataRequest.builder().namespace(CLOUDWATCH_NAMESPACE)
                .metricData(points.collect(Collectors.toList())).build();
        cloudwatch.putMetricData(putRequest);
    }
}
