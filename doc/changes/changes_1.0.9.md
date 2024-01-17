# Cloudwatch Adapter 1.0.9, released 2024-01-17

Code name: Fix CVE-2024-21634 in test dependency `software.amazon.ion:ion-java`

## Summary

This release fixes CVE-2024-21634 (CWE-770: Allocation of Resources Without Limits or Throttling (7.5)) in test dependency `software.amazon.ion:ion-java`.

## Security

* #91: Fixed CVE-2024-21634 in test dependency `software.amazon.ion:ion-java`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.amazonaws:aws-lambda-java-events:3.11.3` to `3.11.4`
* Updated `com.exasol:test-db-builder-java:3.5.2` to `3.5.3`
* Updated `org.apache.logging.log4j:log4j-api:2.22.0` to `2.22.1`
* Updated `org.apache.logging.log4j:log4j-core:2.22.0` to `2.22.1`
* Updated `org.apache.logging.log4j:log4j-slf4j-impl:2.22.0` to `2.22.1`
* Updated `software.amazon.awssdk:cloudwatch:2.21.27` to `2.23.4`
* Updated `software.amazon.awssdk:secretsmanager:2.21.27` to `2.23.4`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.593` to `1.12.638`
* Updated `com.exasol:exasol-testcontainers:6.6.3` to `7.0.0`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.15.3` to `3.15.6`
* Updated `org.mockito:mockito-core:5.7.0` to `5.9.0`
* Updated `org.testcontainers:junit-jupiter:1.19.2` to `1.19.3`
* Updated `org.testcontainers:localstack:1.19.2` to `1.19.3`

### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:2.9.16` to `3.0.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.2.2` to `3.2.3`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.2` to `3.2.3`
* Added `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.16.1` to `2.16.2`
