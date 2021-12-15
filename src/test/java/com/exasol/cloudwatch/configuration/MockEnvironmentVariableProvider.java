package com.exasol.cloudwatch.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * An {@link EnvironmentVariableProvider} based on a {@link Map} that can be used in tests to simulate environment
 * variables.
 */
public class MockEnvironmentVariableProvider implements EnvironmentVariableProvider {
    private final Map<String, String> variables = new HashMap<>();

    @Override
    public String getEnv(final String name) {
        return this.variables.get(name);
    }

    public void put(final String name, final String value) {
        this.variables.put(name, value);
    }
}
