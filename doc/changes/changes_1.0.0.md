# cloudwatch-adapter 1.0.0, released 2022-02-22

Code name: Add metrics for backups

## Summary

This release adds metrics for backup events (`EVENT_BACKUP_END`, `EVENT_BACKUP_START` and `EVENT_BACKUP_ABORTED`) and the backup duration (`BACKUP_DURATION`).

## Features

* #57: Added events for successful and failed backups as well as the backup duration

## Dependency Updates

### Compile Dependency Updates

* Updated `com.amazonaws:aws-lambda-java-log4j2:1.3.0` to `1.5.1`
* Updated `com.exasol:exasol-jdbc:7.1.3` to `7.1.4`
* Updated `com.exasol:test-db-builder-java:3.2.1` to `3.3.1`
* Updated `org.apache.logging.log4j:log4j-api:2.17.0` to `2.17.1`
* Updated `org.apache.logging.log4j:log4j-core:2.17.0` to `2.17.1`
* Updated `org.apache.logging.log4j:log4j-slf4j18-impl:2.17.0` to `2.17.1`
* Updated `software.amazon.awssdk:cloudwatch:2.17.100` to `2.17.133`
* Updated `software.amazon.awssdk:secretsmanager:2.17.100` to `2.17.133`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.129` to `1.12.162`
* Updated `com.exasol:exasol-testcontainers:5.1.1` to `6.1.1`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.8` to `3.9`
* Updated `org.mockito:mockito-core:4.1.0` to `4.3.1`
* Updated `org.testcontainers:junit-jupiter:1.16.2` to `1.16.3`
* Updated `org.testcontainers:localstack:1.16.2` to `1.16.3`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:0.7.1` to `1.0.0`
* Updated `io.github.zlika:reproducible-build-maven-plugin:0.14` to `0.15`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.8.1` to `3.10.0`
* Updated `org.apache.maven.plugins:maven-jar-plugin:3.2.0` to `3.2.2`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.8.1` to `2.9.0`
* Updated `org.itsallcode:openfasttrace-maven-plugin:1.2.1` to `1.4.0`
* Updated `org.sonatype.ossindex.maven:ossindex-maven-plugin:3.1.0` to `3.2.0`
