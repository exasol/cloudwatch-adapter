# cloudwatch-adapter 1.0.6, released 2023-03-15

Code name: Remove outdated maven.exasol.com

## Summary

This release removes the outdated Maven repository `maven.exasol.com`.

## Features

* #82: Removed outdated Maven repository `maven.exasol.com`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.amazonaws:aws-lambda-java-core:1.2.1` to `1.2.2`
* Updated `com.amazonaws:aws-lambda-java-events:3.11.0` to `3.11.1`
* Updated `com.exasol:error-reporting-java:1.0.0` to `1.0.1`
* Updated `com.exasol:exasol-jdbc:7.1.11` to `7.1.17`
* Updated `com.exasol:test-db-builder-java:3.4.1` to `3.4.2`
* Removed `com.fasterxml.jackson.core:jackson-databind:2.14.0-rc2`
* Updated `org.apache.logging.log4j:log4j-api:2.19.0` to `2.20.0`
* Updated `org.apache.logging.log4j:log4j-core:2.19.0` to `2.20.0`
* Updated `org.apache.logging.log4j:log4j-slf4j-impl:2.19.0` to `2.20.0`
* Updated `software.amazon.awssdk:cloudwatch:2.18.2` to `2.20.24`
* Updated `software.amazon.awssdk:secretsmanager:2.18.2` to `2.20.24`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.327` to `1.12.427`
* Updated `com.exasol:exasol-testcontainers:6.3.0` to `6.5.1`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.10.1` to `3.14`
* Updated `org.junit.jupiter:junit-jupiter:5.9.1` to `5.9.2`
* Updated `org.mockito:mockito-core:4.8.1` to `5.2.0`
* Updated `org.testcontainers:junit-jupiter:1.17.5` to `1.17.6`
* Updated `org.testcontainers:localstack:1.17.5` to `1.17.6`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.1.2` to `1.2.2`
* Updated `com.exasol:project-keeper-maven-plugin:2.8.0` to `2.9.4`
* Updated `io.github.zlika:reproducible-build-maven-plugin:0.15` to `0.16`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.1.0` to `3.2.1`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M5` to `3.0.0-M8`
* Updated `org.apache.maven.plugins:maven-shade-plugin:3.2.4` to `3.4.1`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M5` to `3.0.0-M8`
* Updated `org.codehaus.mojo:exec-maven-plugin:3.0.0` to `3.1.0`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.2.7` to `1.3.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.10.0` to `2.14.2`
* Updated `org.itsallcode:openfasttrace-maven-plugin:1.5.0` to `1.6.1`
