# Cloudwatch Adapter 1.0.8, released 2023-11-21

Code name: Fix vulnerabilities in dependencies

## Summary

This release fixes vulnerabilities in the following dependencies:
* `org.eclipse.parsson:parsson` in runtime
    * CVE-2023-4043, severity CWE-20: Improper Input Validation (7.5)
* `io.netty:netty-handler` in runtime
    * CVE-2023-4586, severity CWE-300: Channel Accessible by Non-Endpoint ('Man-in-the-Middle') (6.5)
* `org.apache.commons:commons-compress` in test
    * CVE-2023-42503, severity CWE-20: Improper Input Validation (5.5)

The release also runs integration tests with Exasol DB version 8.

## Security

* #89: Fix vulnerabilities in dependencies

## Dependency Updates

### Compile Dependency Updates

* Updated `com.amazonaws:aws-lambda-java-core:1.2.2` to `1.2.3`
* Updated `com.amazonaws:aws-lambda-java-events:3.11.2` to `3.11.3`
* Updated `com.amazonaws:aws-lambda-java-log4j2:1.5.1` to `1.6.0`
* Updated `com.exasol:test-db-builder-java:3.4.2` to `3.5.2`
* Updated `jakarta.json:jakarta.json-api:2.1.2` to `2.1.3`
* Updated `org.apache.logging.log4j:log4j-api:2.20.0` to `2.22.0`
* Updated `org.apache.logging.log4j:log4j-core:2.20.0` to `2.22.0`
* Updated `org.apache.logging.log4j:log4j-slf4j-impl:2.20.0` to `2.22.0`
* Updated `software.amazon.awssdk:cloudwatch:2.20.98` to `2.21.27`
* Updated `software.amazon.awssdk:secretsmanager:2.20.98` to `2.21.27`

### Runtime Dependency Updates

* Updated `org.eclipse.parsson:parsson:1.1.2` to `1.1.5`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.501` to `1.12.593`
* Updated `com.exasol:exasol-testcontainers:6.6.0` to `6.6.3`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.14.3` to `3.15.3`
* Updated `org.junit.jupiter:junit-jupiter:5.9.3` to `5.10.1`
* Updated `org.mockito:mockito-core:5.4.0` to `5.7.0`
* Updated `org.testcontainers:junit-jupiter:1.18.3` to `1.19.2`
* Updated `org.testcontainers:localstack:1.18.3` to `1.19.2`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.2.3` to `1.3.1`
* Updated `com.exasol:project-keeper-maven-plugin:2.9.7` to `2.9.16`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.3.0` to `3.4.1`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0` to `3.2.2`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0` to `3.2.2`
* Updated `org.basepom.maven:duplicate-finder-maven-plugin:1.5.1` to `2.0.1`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.4.1` to `1.5.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.15.0` to `2.16.1`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.9` to `0.8.11`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184` to `3.10.0.2594`
