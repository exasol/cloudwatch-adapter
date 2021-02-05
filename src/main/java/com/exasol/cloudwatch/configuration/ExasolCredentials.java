package com.exasol.cloudwatch.configuration;

import java.util.Objects;

/**
 * This class stores credentials for an Exasol database.
 */
public class ExasolCredentials {
    private final String host;
    private final String port;
    private final String user;
    private final String pass;

    /**
     * Create a new instance of {@link ExasolCredentials}.
     * 
     * @param host exasol host
     * @param port exasol port
     * @param user exasol username
     * @param pass exasol password
     */
    public ExasolCredentials(final String host, final String port, final String user, final String pass) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
    }

    /**
     * Get the host of the Exasol database.
     * 
     * @return Exasol database host
     */
    public String getHost() {
        return this.host;
    }

    /**
     * Get the port of the Exasol database.
     * 
     * @return Exasol database port
     */
    public String getPort() {
        return this.port;
    }

    /**
     * Get the username for the Exasol Database.
     * 
     * @return username for the Exasol Database
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Get the password for the Exasol Database.
     * 
     * @return password for the Exasol Database
     */
    public String getPass() {
        return this.pass;
    }

    @Override
    public String toString() {
        return "ExasolCredentials{" + "host='" + this.host + '\'' + ", port='" + this.port + '\'' + ", user='"
                + this.user + '\'' + ", pass=<HIDDEN>" + '}';
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        final ExasolCredentials that = (ExasolCredentials) other;
        return Objects.equals(this.host, that.host) && Objects.equals(this.port, that.port)
                && Objects.equals(this.user, that.user) && Objects.equals(this.pass, that.pass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.host, this.port, this.user, this.pass);
    }
}
