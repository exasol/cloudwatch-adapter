package com.exasol.cloudwatch.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

class EnvironmentVariableProviderTest {
    @Test
    void testMissingEnvVariableReturnsNull() {
        assertThat(EnvironmentVariableProvider.getDefault().getEnv("this-env-variable-does-not-exist"), nullValue());
    }

    @Test
    void testDefinedEnvVariableIsNotEmpty() {
        assertThat(EnvironmentVariableProvider.getDefault().getEnv("PATH"), not(emptyString()));
    }
}
