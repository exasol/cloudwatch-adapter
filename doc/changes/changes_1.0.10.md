# Cloudwatch Adapter 1.0.10, released 2024-03-14

Code name: Fix CVE-2024-26308 and CVE-2024-25710 in test dependency

## Summary

This release fixed CVE-2024-26308 and CVE-2024-25710 in test dependency `org.apache.commons:commons-compress:1.24.0`.

## Security

* #95: Fixed CVE-2024-26308 in `org.apache.commons:commons-compress:jar:1.24.0:test`
* #94: Fixed CVE-2024-25710 in `org.apache.commons:commons-compress:jar:1.24.0:test`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:7.1.20` to `24.0.0`
* Updated `com.exasol:test-db-builder-java:3.5.3` to `3.5.4`
* Updated `org.apache.logging.log4j:log4j-api:2.22.1` to `2.23.1`
* Updated `org.apache.logging.log4j:log4j-core:2.22.1` to `2.23.1`
* Updated `org.apache.logging.log4j:log4j-slf4j-impl:2.22.1` to `2.23.1`
* Updated `software.amazon.awssdk:cloudwatch:2.23.4` to `2.25.9`
* Updated `software.amazon.awssdk:secretsmanager:2.23.4` to `2.25.9`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.638` to `1.12.679`
* Updated `com.exasol:exasol-testcontainers:7.0.0` to `7.0.1`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.15.6` to `3.15.8`
* Updated `org.junit.jupiter:junit-jupiter:5.10.1` to `5.10.2`
* Updated `org.mockito:mockito-core:5.9.0` to `5.11.0`
* Updated `org.testcontainers:junit-jupiter:1.19.3` to `1.19.7`
* Updated `org.testcontainers:localstack:1.19.3` to `1.19.7`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.3.1` to `2.0.1`
* Updated `com.exasol:project-keeper-maven-plugin:3.0.0` to `4.2.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.11.0` to `3.12.1`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.2.3` to `3.2.5`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.3` to `3.2.5`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.5.0` to `1.6.0`
