# Cloudwatch Adapter 1.0.7, released 2023-07-04

Code name: Update dependencies on top of 1.0.6

## Summary

This release fixes vulnerability CVE-2023-34462 in runtime dependency `io.netty:netty-handler`.

## Security

* #87: Updated dependencies

## Refactoring

* #85: Migrated CI isolation to AWS CDK v2

## Dependency Updates

### Compile Dependency Updates

* Updated `com.amazonaws:aws-lambda-java-events:3.11.1` to `3.11.2`
* Updated `com.exasol:exasol-jdbc:7.1.17` to `7.1.20`
* Added `jakarta.json:jakarta.json-api:2.1.2`
* Removed `javax.json:javax.json-api:1.1.4`
* Removed `org.glassfish:javax.json:1.1.4`
* Updated `software.amazon.awssdk:cloudwatch:2.20.24` to `2.20.98`
* Updated `software.amazon.awssdk:secretsmanager:2.20.24` to `2.20.98`

### Runtime Dependency Updates

* Added `org.eclipse.parsson:parsson:1.1.2`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.427` to `1.12.501`
* Updated `com.exasol:exasol-testcontainers:6.5.1` to `6.6.0`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.14` to `3.14.3`
* Updated `org.junit.jupiter:junit-jupiter:5.9.2` to `5.9.3`
* Updated `org.mockito:mockito-core:5.2.0` to `5.4.0`
* Updated `org.testcontainers:junit-jupiter:1.17.6` to `1.18.3`
* Updated `org.testcontainers:localstack:1.17.6` to `1.18.3`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.2.2` to `1.2.3`
* Updated `com.exasol:project-keeper-maven-plugin:2.9.4` to `2.9.7`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.10.1` to `3.11.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.2.1` to `3.3.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M8` to `3.0.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M8` to `3.0.0`
* Added `org.basepom.maven:duplicate-finder-maven-plugin:1.5.1`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.3.0` to `1.4.1`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.14.2` to `2.15.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.8` to `0.8.9`
