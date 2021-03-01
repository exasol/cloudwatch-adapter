package com.exasol.cloudwatch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;

import java.time.Instant;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.exasol.cloudwatch.exasolmetrics.ExasolMetricDatum;
import com.exasol.cloudwatch.exasolmetrics.ExasolUnit;

import software.amazon.awssdk.services.cloudwatch.model.MetricDatum;
import software.amazon.awssdk.services.cloudwatch.model.StandardUnit;

class ExasolToCloudwatchMetricDatumConverterTest {

    private static final ExasolToCloudwatchMetricDatumConverter CONVERTER = new ExasolToCloudwatchMetricDatumConverter(
            "TEST");

    @ParameterizedTest
    @CsvSource({ //
            "COUNT, 1.0", //
            "PERCENT, 1.0", //
            "NONE, 1.0", //
            "SECONDS, 1.0", //
            "GIBIBYTES, 1.07374", //
            "MEBIBYTES_PER_SECOND, 1.04858", //
            "MEBIBYTES, 1.04858", //
    })
    void testValueConversion(final ExasolUnit unit, final double expectedResult) {
        final MetricDatum result = CONVERTER
                .convert(new ExasolMetricDatum("MY_METRIC", unit, Instant.now(), 1.0, "MAIN"));
        assertThat(result.value(), closeTo(expectedResult, 0.0001));
    }

    @ParameterizedTest
    @CsvSource({ //
            "COUNT, COUNT", //
            "PERCENT, PERCENT", //
            "NONE, NONE", //
            "SECONDS, SECONDS", //
            "GIBIBYTES, GIGABYTES", //
            "MEBIBYTES_PER_SECOND, MEGABYTES_SECOND", //
            "MEBIBYTES, MEGABYTES", //
    })
    void testUnitConversion(final ExasolUnit unit, final StandardUnit expectedCloudWatchUnit) {
        final MetricDatum result = CONVERTER
                .convert(new ExasolMetricDatum("MY_METRIC", unit, Instant.now(), 1.0, "MAIN"));
        assertThat(result.unit(), equalTo(expectedCloudWatchUnit));
    }
}