package com.exasol.cloudwatch;

import com.exasol.errorreporting.ExaError;

import software.amazon.awssdk.services.cloudwatch.model.*;

public class ExasolToCloudwatchMetricDatumConverter {

    public static final String CLUSTER_NAME_DIMENSION_KEY = "Cluster Name";
    public static final String DEPLOYMENT_DIMENSION_KEY = "Deployment";
    private static final double MIB_TO_MB_FACTOR = 1.048576;
    private static final double GIB_TO_GB_FACTOR = 1.073741824;
    private final Dimension deploymentDimension;

    /**
     * Create a new instance of {@link ExasolToCloudwatchMetricDatumConverter}.
     * 
     * @param deploymentName name of the Exasol deployment (used as dimension for the metrics)
     */
    public ExasolToCloudwatchMetricDatumConverter(final String deploymentName) {
        this.deploymentDimension = Dimension.builder().name(DEPLOYMENT_DIMENSION_KEY).value(deploymentName).build();
    }

    public MetricDatum convert(final ExasolStatisticsTableMetricDatum point) {
        switch (point.getMetric().getUnit()) {
        case COUNT:
            return buildMetricDatum(point, StandardUnit.COUNT, 1.0);
        case PERCENT:
            return buildMetricDatum(point, StandardUnit.PERCENT, 1.0);
        case MEBIBYTES:
            return buildMetricDatum(point, StandardUnit.MEGABYTES, MIB_TO_MB_FACTOR);
        case GIBIBYTES:
            return buildMetricDatum(point, StandardUnit.GIGABYTES, GIB_TO_GB_FACTOR);
        case MEBIBYTES_PER_SECOND:
            return buildMetricDatum(point, StandardUnit.MEGABYTES_SECOND, MIB_TO_MB_FACTOR);
        case SECONDS:
            return buildMetricDatum(point, StandardUnit.SECONDS, 1.0);
        case NONE:
            return buildMetricDatum(point, StandardUnit.NONE, 1.0);
        default:
            throw new IllegalStateException(ExaError.messageBuilder("F-CWA-14")
                    .message("Converting of Metrics with unit {{unit}} is not implemented.").ticketMitigation()
                    .parameter("unit", point.getMetric().getUnit()).toString());
        }
    }

    private MetricDatum buildMetricDatum(final ExasolStatisticsTableMetricDatum point,
            final StandardUnit cloudwatchUnit, final double factor) {
        return MetricDatum.builder().metricName(point.getMetric().name())//
                .timestamp(point.getTimestamp())//
                .dimensions(this.deploymentDimension,
                        Dimension.builder().name(CLUSTER_NAME_DIMENSION_KEY).value(point.getClusterName()).build())
                .value(point.getValue() * factor)//
                .unit(cloudwatchUnit)//
                .build();
    }
}
