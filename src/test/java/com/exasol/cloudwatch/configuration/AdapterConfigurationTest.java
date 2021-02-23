package com.exasol.cloudwatch.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class AdapterConfigurationTest {
    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(AdapterConfiguration.class).verify();
    }

    @Test
    void testToString() {
        final ExasolCredentials credentials = new ExasolCredentials("127.0.0.1", "8563", "myUser", "myPass");
        final AdapterConfiguration configuration = new AdapterConfiguration("MyExasol", List.of("USERS"), credentials);
        assertThat(configuration.toString(), equalTo(
                "AdapterConfiguration{deploymentName='MyExasol', enabledMetrics=[USERS], exasolCredentials=ExasolCredentials{host='127.0.0.1', port='8563', user='myUser', pass=<HIDDEN>}}"));
    }
}