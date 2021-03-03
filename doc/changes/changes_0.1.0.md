# CloudWatch-adapter 0.1.0, released 2021-03-03

Code name: Initial release

## Summary

This is the very first release of the Exasol – CloudWatch adapter.

Known issue:

* In case your Exasol database uses a timezone with time-shift as `DBTIMEZONE`, this adapter will **not report** the hour when the time is shifted back, since Exasol stores the statistics entries in the `DBTIMEZONE` and by that, the log entries are ambiguous in that hour (see [#2](https://github.com/exasol/cloudwatch-adapter/issues/2)).
* Due to a bug in SAM we can not publish the app with a retry count set to 0. This can lead to duplicate reported data if the Lambda fails after reporting some points and EventBridge triggers it again (see [#21](https://github.com/exasol/cloudwatch-adapter/issues/21)).

## Features / Enhancements

* #1: Implemented basic adapter
* #3: Added SAM deployment
* #4: Use AWS Secrets Manager for storing the Exasol credentials
* #7: Added additional metrics: `NET`, `SWAP`, `DB_RAM_SIZE`, `NODES`
* #30: Added configuration for VPC
* #35: Added regular reporting of metrics from EXA_SYSTEM_EVENTS
* #37: Report metrics from EXA_SYSTEM_EVENTS only every 4 minutes
* #31: Added wrapper for AWS SDK exceptions

## Documentation

* #28: Added documentation on VPC
* #22: Added a guide for manual deployment
* #33: Added documentation on VPC API endpoints

## Refactoring

* #10: Changed integration tests to use localstack instead of online API
* #12: Changed localstack version from latest to 0.12.6
* #39: Refactored the exasol metric class structure

## Bugfixes

* #8: Fixed wrong metrics
* #14: Removed failing SAM validate CI, since it requires AWS access
* #19: Fixed SAM template (`ClassNotFoundException` on deployment)

## Dependency Updates

* Added `org.apache.logging.log4j:log4j-core:2.14.0`
* Added `com.amazonaws:aws-lambda-java-events:3.7.0`
* Added `org.junit.jupiter:junit-jupiter:5.7.1`
* Added `org.hamcrest:hamcrest:2.2`
* Added `com.github.stefanbirkner:system-lambda:1.2.0`
* Added `org.testcontainers:localstack:1.15.2`
* Added `com.exasol:exasol-jdbc:7.0.7`
* Added `com.amazonaws:aws-java-sdk-s3:1.11.966`
* Added `software.amazon.awssdk:cloudwatch:2.16.10`
* Added `com.exasol:error-reporting-java:0.2.2`
* Added `org.apache.logging.log4j:log4j-slf4j18-impl:2.14.0`
* Added `org.glassfish:javax.json:1.1.4`
* Added `org.itsallcode:junit5-system-extensions:1.1.0`
* Added `org.mockito:mockito-core:3.8.0`
* Added `org.apache.logging.log4j:log4j-api:2.14.0`
* Added `com.exasol:exasol-testcontainers:3.5.1`
* Added `javax.json:javax.json-api:1.1.4`
* Added `com.exasol:test-db-builder-java:3.1.0`
* Added `org.testcontainers:junit-jupiter:1.15.2`
* Added `com.amazonaws:aws-lambda-java-core:1.2.1`
* Added `com.amazonaws:aws-lambda-java-log4j2:1.2.0`
* Added `nl.jqno.equalsverifier:equalsverifier:3.5.5`
* Added `software.amazon.awssdk:secretsmanager:2.16.10`