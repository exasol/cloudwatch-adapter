package com.exasol.cloudwatch.exasolmetrics;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ExasolEventTest {
    @ParameterizedTest
    @CsvSource({ "EVENT_BACKUP_START, BACKUP_START", //
            "EVENT_BACKUP_END, BACKUP_END", //
            "EVENT_BACKUP_ABORTED, BACKUP_ABORTED" })
    void forNameSucceeds(final String name, final ExasolEvent expected) {
        assertThat(ExasolEvent.forName(name), equalTo(expected));
    }

    @ParameterizedTest
    @CsvSource({ "BACKUP_START", "BACKUP_END", "BACKUP_ABORTED", "event_backup_aborted", "unknown" })
    void forNameFails(final String name) {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ExasolEvent.forName(name));
        assertThat(exception.getMessage(), equalTo("E-CWA-20: Unknown event metric '" + name
                + "'. Use one of the available event metrics: [BACKUP_START, BACKUP_END, BACKUP_ABORTED]"));
    }
}
