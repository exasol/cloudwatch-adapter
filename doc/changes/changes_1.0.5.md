# cloudwatch-adapter 1.0.5, released 2022-10-25

Code name: Fix vulnerabilities in test dependency

## Summary

This release upgrades test dependency `com.fasterxml.jackson.core:jackson-databind:2.12.6.1` to `2.14.0-rc2` to fix CVE-2022-42003 and CVE-2022-42004.

## Features

* #80: Fixed vulnerabilities in test dependency

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:error-reporting-java:0.4.1` to `1.0.0`
* Updated `com.exasol:test-db-builder-java:3.3.3` to `3.4.1`
* Added `com.fasterxml.jackson.core:jackson-databind:2.14.0-rc2`
* Updated `org.apache.logging.log4j:log4j-api:2.18.0` to `2.19.0`
* Updated `org.apache.logging.log4j:log4j-core:2.18.0` to `2.19.0`
* Added `org.apache.logging.log4j:log4j-slf4j-impl:2.19.0`
* Removed `org.apache.logging.log4j:log4j-slf4j18-impl:2.18.0`
* Updated `software.amazon.awssdk:cloudwatch:2.17.245` to `2.18.2`
* Updated `software.amazon.awssdk:secretsmanager:2.17.245` to `2.18.2`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.274` to `1.12.327`
* Updated `com.exasol:exasol-testcontainers:6.1.2` to `6.3.0`
* Updated `org.junit.jupiter:junit-jupiter:5.9.0` to `5.9.1`
* Updated `org.mockito:mockito-core:4.6.1` to `4.8.1`
* Updated `org.testcontainers:junit-jupiter:1.17.3` to `1.17.5`
* Updated `org.testcontainers:localstack:1.17.3` to `1.17.5`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.1.1` to `1.1.2`
* Updated `com.exasol:project-keeper-maven-plugin:2.5.0` to `2.8.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.0.0` to `3.1.0`
