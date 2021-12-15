package com.exasol.cloudwatch.configuration;

/**
 * Implementors of this interface allow access to the system's environment variables by encapsulating
 * {@link System#getenv(String)}. The advantage over using {@link System#getenv(String)} directly is that you can mock
 * this interface in unit tests.
 */
@FunctionalInterface
public interface EnvironmentVariableProvider {

    /**
     * Gets the value of the specified environment variable. An environment variable is a system-dependent external
     * named value.
     *
     * @param name the name of the environment variable
     * @return the string value of the variable, or {@code null} if the variable is not defined in the system
     *         environment
     * @see System#getenv(String)
     */
    public String getEnv(String name);

    /**
     * Gets the default implementation that returns {@link System#getenv(String)}.
     *
     * @return the default {@link EnvironmentVariableProvider}.
     */
    public static EnvironmentVariableProvider getDefault() {
        return System::getenv;
    }
}
