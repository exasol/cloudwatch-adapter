# cloudwatch-adapter 0.2.0, released 2021-12-15

Code name: Support TLS certificate fingerprints

This version allows you to configure the TLS certificate fingerprint of the Exasol database. This is useful when Exasol uses a self-signed certificate. See the [documentation](../../README.md#store-credentials-in-aws-secrets-manager) how to configure the fingerprint.

## Features

* #52: Added support for TLS certificate fingerprints

## Dependency Updates

### Compile Dependency Updates

* Updated `com.amazonaws:aws-lambda-java-events:3.9.0` to `3.11.0`
* Updated `com.exasol:error-reporting-java:0.4.0` to `0.4.1`
* Updated `com.exasol:exasol-jdbc:7.0.11` to `7.1.3`
* Updated `com.exasol:test-db-builder-java:3.2.0` to `3.2.1`
* Updated `software.amazon.awssdk:cloudwatch:2.17.9` to `2.17.100`
* Updated `software.amazon.awssdk:secretsmanager:2.17.9` to `2.17.100`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.37` to `1.12.128`
* Updated `com.exasol:exasol-testcontainers:4.0.0` to `5.1.1`
* Removed `com.github.stefanbirkner:system-lambda:1.2.0`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.7` to `3.8`
* Updated `org.junit.jupiter:junit-jupiter:5.7.2` to `5.8.2`
* Updated `org.mockito:mockito-core:3.11.2` to `4.1.0`
* Updated `org.testcontainers:junit-jupiter:1.16.0` to `1.16.2`
* Updated `org.testcontainers:localstack:1.16.0` to `1.16.2`

### Plugin Dependency Updates

* Updated `com.exasol:artifact-reference-checker-maven-plugin:0.3.1` to `0.4.0`
* Updated `com.exasol:error-code-crawler-maven-plugin:0.5.1` to `0.7.1`
* Updated `com.exasol:project-keeper-maven-plugin:0.10.0` to `1.3.4`
* Updated `io.github.zlika:reproducible-build-maven-plugin:0.13` to `0.14`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.0.0-M3` to `3.0.0`
* Updated `org.apache.maven.plugins:maven-shade-plugin:3.2.2` to `3.2.4`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.7` to `2.8.1`
* Updated `org.itsallcode:openfasttrace-maven-plugin:1.0.0` to `1.2.1`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.5` to `0.8.7`
