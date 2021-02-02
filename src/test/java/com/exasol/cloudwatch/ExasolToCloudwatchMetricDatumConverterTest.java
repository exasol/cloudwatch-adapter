package com.exasol.cloudwatch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;

import java.time.Instant;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import software.amazon.awssdk.services.cloudwatch.model.MetricDatum;
import software.amazon.awssdk.services.cloudwatch.model.StandardUnit;

class ExasolToCloudwatchMetricDatumConverterTest {

    private static final ExasolToCloudwatchMetricDatumConverter CONVERTER = new ExasolToCloudwatchMetricDatumConverter(
            "TEST");

    @ParameterizedTest
    @CsvSource({ //
            "QUERIES, 1.0", //
            "CPU, 1.0", //
            "LOAD, 1.0", //
            "REMOTE_WRITE_DURATION, 1.0", //
            "RAW_OBJECT_SIZE, 1.07374", //
            "HDD_READ, 1.04858", //
            "LOCAL_READ_SIZE, 1.04858", //
    })
    void testValueConversion(final ExasolStatisticsTableMetric metric, final double expectedResult) {
        final MetricDatum result = CONVERTER
                .convert(new ExasolStatisticsTableMetricDatum(metric, Instant.now(), 1.0, "MAIN"));
        assertThat(result.value(), closeTo(expectedResult, 0.0001));
    }

    @ParameterizedTest
    @CsvSource({ //
            "QUERIES, COUNT", //
            "CPU, PERCENT", //
            "LOAD, NONE", //
            "REMOTE_WRITE_DURATION, SECONDS", //
            "RAW_OBJECT_SIZE, GIGABYTES", //
            "HDD_READ, MEGABYTES_SECOND", //
            "LOCAL_READ_SIZE, MEGABYTES", //
    })
    void testUnitConversion(final ExasolStatisticsTableMetric metric, final StandardUnit expectedCloudWatchUnit) {
        final MetricDatum result = CONVERTER
                .convert(new ExasolStatisticsTableMetricDatum(metric, Instant.now(), 1.0, "MAIN"));
        assertThat(result.unit(), equalTo(expectedCloudWatchUnit));
    }
}