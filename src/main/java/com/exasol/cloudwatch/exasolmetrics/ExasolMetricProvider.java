package com.exasol.cloudwatch.exasolmetrics;

import java.util.List;

/**
 * This class is the public interface for accessing metrics from the Exasol database.
 */
public class ExasolMetricProvider extends CompoundExasolMetricReaderFactory {

    /**
     * Create a new instance of {@link ExasolMetricProvider}.
     */
    public ExasolMetricProvider() {
        super(createReaders());
    }

    private static List<ExasolMetricReaderFactory> createReaders() {
        return List.of(//
                new ExasolStatisticsTableRegularMetricReaderFactory(),
                new ExasolStatisticsTableEventsMetricReaderFactory(),
                new ExasolEventMetricsReaderFactory());
    }
}
