package com.exasol.cloudwatch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ExasolStatisticsTableMetricTest {

    @Test
    void testParseUnknown() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ExasolStatisticsTableMetric.parse("UNKNOWN"));
        assertThat(exception.getMessage(), equalTo(
                "E-CWA-9: Unknown metric 'UNKNOWN'. Supported metrics are ['RAW_OBJECT_SIZE', 'MEM_OBJECT_SIZE', 'AUXILIARY_SIZE', 'STATISTICS_SIZE', 'RECOMMENDED_DB_RAM_SIZE', 'STORAGE_SIZE', 'USE', 'TEMP_SIZE', 'OBJECT_COUNT', 'LOAD', 'CPU', 'TEMP_DB_RAM', 'PERSISTENT_DB_RAM', 'HDD_READ', 'HDD_WRITE', 'LOCAL_READ_SIZE', 'LOCAL_READ_DURATION', 'LOCAL_WRITE_DURATION', 'CACHE_READ_SIZE', 'CACHE_READ_DURATION', 'CACHE_WRITE_SIZE', 'CACHE_WRITE_DURATION', 'REMOTE_READ_SIZE', 'REMOTE_READ_DURATION', 'REMOTE_WRITE_SIZE', 'REMOTE_WRITE_DURATION', 'USERS', 'QUERIES']."));
    }
}
