package com.exasol.cloudwatch.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class ExasolCredentialsTest {

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(ExasolCredentials.class).verify();
    }

    @Test
    void testToString() {
        final ExasolCredentials credentials = new ExasolCredentials("127.0.0.1", "8563", "myUser", "myPass",
                "myFingerprint");
        assertThat(credentials.toString(), equalTo(
                "ExasolCredentials{host='127.0.0.1', port='8563', user='myUser', pass=<HIDDEN>, certificate fingerprint='myFingerprint'}"));
    }
}
