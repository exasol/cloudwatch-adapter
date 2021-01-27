# Exasol – AWS Cloudwatch Adapter

This adapter transfers the statistics from the Exasol database to [AWS CloudWatch](https://aws.amazon.com/de/cloudwatch/) metrics. This adapter runs independently of the Exasol database in an AWS Lambda function.

## Configuration

You can configure the adapter using the following environment variables:

* `EXASOL_DEPLOYMENT_NAME`: A name describing the Exasol installation you want to monitor. The adapter adds this name as a [dimension](https://docs.aws.amazon.com/AmazonCloudWatch/latest/monitoring/cloudwatch_concepts.html#Dimension) to the metrics in cloudwatch. This will help you to distinguish the data if you monitor more than one Exasol deployment.

* `METRICS`: A comma-separated list of metrics. If empty, the adapter reports all metrics. Available metrics:
  <!-- DON'T CHANGE THIS BY HAND! Use ReadmeGenerator#printMetrics() instead! -->
  [`RAW_OBJECT_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_DB_SIZE_LAST_DAY), [`MEM_OBJECT_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_DB_SIZE_LAST_DAY), [`AUXILIARY_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_DB_SIZE_LAST_DAY), [`STATISTICS_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_DB_SIZE_LAST_DAY), [`RECOMMENDED_DB_RAM_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_DB_SIZE_LAST_DAY), [`STORAGE_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_DB_SIZE_LAST_DAY), [`USE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_DB_SIZE_LAST_DAY), [`TEMP_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_DB_SIZE_LAST_DAY)
  , [`OBJECT_COUNT`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_DB_SIZE_LAST_DAY), [`LOAD`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`CPU`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`TEMP_DB_RAM`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`PERSISTENT_DB_RAM`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`HDD_READ`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`HDD_WRITE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`LOCAL_READ_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY)
  , [`LOCAL_READ_DURATION`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`LOCAL_WRITE_DURATION`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`CACHE_READ_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`CACHE_READ_DURATION`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`CACHE_WRITE_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`CACHE_WRITE_DURATION`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`REMOTE_READ_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`REMOTE_READ_DURATION`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY)
  , [`REMOTE_WRITE_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`REMOTE_WRITE_DURATION`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`USERS`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_USAGE_LAST_DAY), [`QUERIES`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_USAGE_LAST_DAY)

  For a reference check the [Exasol documentation](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm).

  For technical reasons `QUERIES` is always included. You can't disable it.

## Information for Users

The design of this adapter ensures that now points are written twice, which would lead to wrong statistics. It does, however not assure that all points are written. In case of temporary errors with the Exasol database or the CloudWatch API, it can occur that data points are missing.

* [Changelog](doc/changes/changelog.md)

### Known Bugs

* In case your Exasol database uses a timezone with time-shift as `DBTIMEZONE`, this adapter will **not report** the hour when the time is shifted back, since Exasol stores the statistics entries in the `DBTIMEZONE` and by that, the log entries are ambiguous in that hour (see [#2](https://github.com/exasol/cloudwatch-adapter/issues/2)).