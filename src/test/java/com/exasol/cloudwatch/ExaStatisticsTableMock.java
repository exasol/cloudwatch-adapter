package com.exasol.cloudwatch;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import com.exasol.cloudwatch.exasolmetrics.ExasolStatisticsTable;
import com.exasol.dbbuilder.dialects.Table;
import com.exasol.dbbuilder.dialects.exasol.ExasolObjectFactory;
import com.exasol.dbbuilder.dialects.exasol.ExasolSchema;

public class ExaStatisticsTableMock implements Closeable, AutoCloseable {
    public static final String SCHEMA = "EXA_STATISTICS_MOCK";
    private final Table table;
    private final ExasolSchema schema;

    public ExaStatisticsTableMock(final Connection connection) throws SQLException {
        final ExasolObjectFactory exasolObjectFactory = new ExasolObjectFactory(connection);
        this.schema = exasolObjectFactory.createSchema(SCHEMA);
        this.table = this.schema.createTableBuilder(ExasolStatisticsTable.EXA_USAGE_LAST_DAY.name())
                .column("MEASURE_TIME", "TIMESTAMP")//
                .column("CLUSTER_NAME", "VARCHAR(254) UTF8")//
                .column("USERS", "INTEGER")//
                .column("QUERIES", "INTEGER")//
                .build();
    }

    public ExaStatisticsTableMock addRows(final Stream<Row> rows) {
        this.table.bulkInsert(rows.map(row -> List.of(row.timestamp, row.clusterName, row.users, row.queries)));
        return this;
    }

    @Override
    public void close() {
        this.table.drop();
        this.schema.drop();
    }

    public static class Row {
        /**
         * The docker-db is always set to europe/berlin.
         */
        private static final String DATABASE_TIMEZONE = "Europe/Berlin";
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS");
        private final String timestamp;
        private final String clusterName;
        private final int users;
        private final int queries;

        /**
         * Create an log entry for the mocked statistics table.
         * <p>
         * The instant parameter is a moment. That means that it defines a certain point of time. Exasol stores however
         * no moment for the log entries but a Exasol TIMESTAMP. That's basically a date declaration with year, month,
         * day, hour, ... Exasol writes the logs in dates formatted in the DBTIMEZONE. This method simulates this
         * behaviour.
         * </p>
         *
         * @param instant     point of time the log belongs to
         * @param clusterName value for the CLUSTER_NAME column
         * @param users       value fpr the USERS column
         * @param queries     value for the QUERIES column
         */
        public Row(final Instant instant, final String clusterName, final int users, final int queries) {
            final LocalDateTime instantInDbTime = LocalDateTime.ofInstant(instant, ZoneId.of(DATABASE_TIMEZONE));
            this.timestamp = instantInDbTime.format(FORMATTER);
            this.clusterName = clusterName;
            this.users = users;
            this.queries = queries;
        }

        public Row(final String timestamp, final String clusterName, final int users, final int queries) {
            this.timestamp = timestamp;
            this.clusterName = clusterName;
            this.users = users;
            this.queries = queries;
        }
    }
}
