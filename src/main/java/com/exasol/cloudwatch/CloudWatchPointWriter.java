package com.exasol.cloudwatch;

import java.net.URI;
import java.util.List;
import java.util.stream.Stream;

import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClientBuilder;
import software.amazon.awssdk.services.cloudwatch.model.*;

/**
 * This class writes measurement point to AWS cloud watch using the AWS SDK v2.
 */
public class CloudWatchPointWriter {
    public static final String CLOUDWATCH_NAMESPACE = "Exasol";
    public static final String CLUSTER_NAME_DIMENSION_KEY = "Cluster Name";
    public static final String DEPLOYMENT_DIMENSION_KEY = "Deployment";
    private static final int CHUNK_SIZE = 20;
    private final Dimension deploymentDimension;
    private final URI endpointOverride;

    /**
     * Create a new instance of {@link CloudWatchPointWriter}.
     * 
     * @param deploymentName name of the Exasol deployment (used as dimension for the metrics)
     */
    public CloudWatchPointWriter(final String deploymentName, final URI endpointOverride) {
        this.deploymentDimension = Dimension.builder().name(DEPLOYMENT_DIMENSION_KEY).value(deploymentName).build();
        this.endpointOverride = endpointOverride;
    }

    /**
     * Write a list of points to AWS CloudWatch.
     * 
     * @param systemTableDataPoints points to write
     */
    public void putPointsInChunks(final List<SystemTableDataPoint> systemTableDataPoints) {
        try (final CloudWatchClient cloudwatch = buildCloudWatchClient()) {
            for (int chunkCounter = 0; chunkCounter * CHUNK_SIZE < systemTableDataPoints.size(); chunkCounter++) {
                final Stream<SystemTableDataPoint> chuck = systemTableDataPoints.stream()
                        .skip((long) chunkCounter * CHUNK_SIZE).limit(CHUNK_SIZE);
                putPoints(cloudwatch, chuck);
            }
        }
    }

    private CloudWatchClient buildCloudWatchClient() {
        final CloudWatchClientBuilder builder = CloudWatchClient.builder();
        if (this.endpointOverride != null) {
            builder.endpointOverride(this.endpointOverride);
        }
        return builder.build();
    }

    private void putPoints(final CloudWatchClient cloudwatch,
            final Stream<SystemTableDataPoint> systemTableDataPoints) {
        final MetricDatum[] metricData = systemTableDataPoints.map(point -> MetricDatum.builder()
                .metricName(point.getMetric().name()).value(point.getValue()).unit(point.getMetric().getUnit())
                .timestamp(point.getTimestamp())
                .dimensions(this.deploymentDimension,
                        Dimension.builder().name(CLUSTER_NAME_DIMENSION_KEY).value(point.getClusterName()).build())
                .build()).toArray(MetricDatum[]::new);
        final PutMetricDataRequest putRequest = PutMetricDataRequest.builder().namespace(CLOUDWATCH_NAMESPACE)
                .metricData(metricData).build();
        cloudwatch.putMetricData(putRequest);
    }
}
