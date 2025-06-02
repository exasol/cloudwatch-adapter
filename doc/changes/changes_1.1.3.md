# Cloudwatch Adapter 1.1.3, released 2025-06-02

Code name: Security updates

## Summary

This release is a security update. We updated the dependencies of the project to fix transitive security issues.

We also added an exception for the OSSIndex for CVE-2024-55551, which is a false positive in Exasol's JDBC driver.
This issue has been fixed quite a while back now, but the OSSIndex unfortunately does not contain the fix version of 24.2.1 (2024-12-10) set.

## Security

* #108: Fix CVE-2024-55551 in com.exasol:exasol-jdbc:jar:7.1.20:runtime

## Dependency Updates

### Compile Dependency Updates

* Updated `com.amazonaws:aws-lambda-java-core:1.2.3` to `1.3.0`
* Updated `com.amazonaws:aws-lambda-java-events:3.14.0` to `3.15.0`
* Updated `com.exasol:exasol-jdbc:24.2.1` to `25.2.3`
* Updated `com.exasol:test-db-builder-java:3.6.0` to `3.6.1`
* Updated `software.amazon.awssdk:cloudwatch:2.30.19` to `2.31.54`
* Updated `software.amazon.awssdk:secretsmanager:2.30.19` to `2.31.54`

### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:7.1.3` to `7.1.5`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.19` to `3.19.4`
* Added `org.junit.jupiter:junit-jupiter-api:5.13.0`
* Added `org.junit.jupiter:junit-jupiter-params:5.13.0`
* Removed `org.junit.jupiter:junit-jupiter:5.11.4`
* Updated `org.mockito:mockito-core:5.15.2` to `5.18.0`
* Updated `org.testcontainers:junit-jupiter:1.20.4` to `1.21.1`
* Updated `org.testcontainers:localstack:1.20.4` to `1.21.1`

### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:4.5.0` to `5.1.0`
* Added `io.github.git-commit-id:git-commit-id-maven-plugin:9.0.1`
* Removed `io.github.zlika:reproducible-build-maven-plugin:0.17`
* Added `org.apache.maven.plugins:maven-artifact-plugin:3.6.0`
* Updated `org.apache.maven.plugins:maven-clean-plugin:3.4.0` to `3.4.1`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.13.0` to `3.14.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.5.2` to `3.5.3`
* Updated `org.apache.maven.plugins:maven-install-plugin:3.1.3` to `3.1.4`
* Updated `org.apache.maven.plugins:maven-shade-plugin:3.4.1` to `3.6.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.5.2` to `3.5.3`
* Updated `org.codehaus.mojo:exec-maven-plugin:3.1.0` to `3.5.1`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.6.0` to `1.7.0`
* Updated `org.itsallcode:openfasttrace-maven-plugin:1.6.1` to `2.3.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.12` to `0.8.13`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:5.0.0.4389` to `5.1.0.4751`
