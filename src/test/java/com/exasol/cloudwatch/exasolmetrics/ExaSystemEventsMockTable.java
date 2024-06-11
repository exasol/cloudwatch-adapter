package com.exasol.cloudwatch.exasolmetrics;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.*;
import java.time.Instant;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * This is class prepares a mocked table {@code EXA_SYSTEM_EVENTS} with arbitrary content.
 */
class ExaSystemEventsMockTable implements AutoCloseable {
    static final String MOCK_SCHEMA = "MOCK_SCHEMA";
    private static final String EXA_SYSTEM_EVENTS = "EXA_SYSTEM_EVENTS";
    private final Connection exasolConnection;
    private final Statement statement;
    private final Calendar timeZoneCalendar;
    private final String dbVersion;

    ExaSystemEventsMockTable(final Connection exasolConnection) throws SQLException {
        this(exasolConnection, TimeZone.getTimeZone("UTC"));
    }

    private ExaSystemEventsMockTable(final Connection exasolConnection, final TimeZone timeZone) throws SQLException {
        this.exasolConnection = exasolConnection;
        this.statement = exasolConnection.createStatement();
        this.statement.executeUpdate("CREATE SCHEMA " + MOCK_SCHEMA);
        this.statement.executeUpdate(
                "CREATE TABLE " + MOCK_SCHEMA + "." + EXA_SYSTEM_EVENTS + " LIKE EXA_STATISTICS." + EXA_SYSTEM_EVENTS);
        this.timeZoneCalendar = Calendar.getInstance(timeZone);
        this.dbVersion = getDbVersion(statement);
    }

    private static String getDbVersion(final Statement stmt) throws SQLException {
        try (ResultSet rs = stmt
                .executeQuery("select PARAM_VALUE from SYS.EXA_METADATA where PARAM_NAME='databaseProductVersion'")) {
            assertTrue(rs.next());
            return rs.getString(1);
        }
    }

    static ExaSystemEventsMockTable forDbTimeZone(final Connection exasolConnection) throws SQLException {
        return new ExaSystemEventsMockTable(exasolConnection, getDbTimezone(exasolConnection));
    }

    private static TimeZone getDbTimezone(final Connection exasolConnection) throws SQLException {
        try (final Statement stmt = exasolConnection.createStatement()) {
            stmt.execute("select dbtimezone");
            final ResultSet resultSet = stmt.getResultSet();
            assertTrue(resultSet.next());
            return parseTimeZone(resultSet.getString(1));
        }
    }

    private static TimeZone parseTimeZone(final String timeZoneId) {
        final String convertedTimeZoneId = convertTimeZoneId(timeZoneId);
        final TimeZone timeZone = TimeZone.getTimeZone(convertedTimeZoneId);
        assertThat(timeZone.getID().toUpperCase(), equalTo(timeZoneId.toUpperCase()));
        return timeZone;
    }

    private static String convertTimeZoneId(final String timeZoneId) {
        if (timeZoneId.equals("EUROPE/BERLIN")) {
            return "Europe/Berlin";
        }
        return timeZoneId;
    }

    void insert(final Instant measureTime, final double dbRamSize, final int nodes, final String clusterName)
            throws SQLException {
        insert(measureTime, null, dbRamSize, nodes, clusterName, null);
    }

    void insert(final Instant measureTime, final String clusterName, final String eventType) throws SQLException {
        insert(measureTime, eventType, 0, 0, clusterName, null);
    }

    private void insert(final Instant measureTime, final String eventType, final double dbRamSize, final int nodes,
            final String clusterName, final Integer vcpu) throws SQLException {
        String sql = "INSERT INTO " + MOCK_SCHEMA + "." + EXA_SYSTEM_EVENTS + " VALUES(?, ?, ?, '',? ,? , ''";
        if (isExasol8()) {
            sql += ",?";
        }
        sql += ")";
        try (final PreparedStatement insertStatement = this.exasolConnection.prepareStatement(sql)) {
            insertStatement.setString(1, clusterName);
            insertStatement.setTimestamp(2, Timestamp.from(measureTime), this.timeZoneCalendar);
            insertStatement.setString(3, eventType);
            insertStatement.setInt(4, nodes);
            insertStatement.setDouble(5, dbRamSize);
            if (isExasol8()) {
                insertStatement.setObject(6, vcpu);
            }
            insertStatement.executeUpdate();
        }
    }

    private boolean isExasol8() {
        return dbVersion.startsWith("8");
    }

    @Override
    public void close() throws SQLException {
        this.statement.executeUpdate("DROP TABLE " + MOCK_SCHEMA + "." + EXA_SYSTEM_EVENTS);
        this.statement.executeUpdate("DROP SCHEMA " + MOCK_SCHEMA);
        this.statement.close();
    }
}
