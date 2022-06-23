# cloudwatch-adapter 1.0.2, released 2022-06-23

Code name: Dependency Updates

## Summary

**Aborted:** This release was aborted since the release process was broken. Use 1.0.3. instead.

In this release we updated dependencies and by that fixed the following security vulnerabilities: `CVE-2022-24823`, `sonatype-2020-0026`, `CVE-2021-22569`, `CVE-2020-36518`.

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:test-db-builder-java:3.3.1` to `3.3.2`
* Updated `org.apache.logging.log4j:log4j-api:2.17.1` to `2.17.2`
* Updated `org.apache.logging.log4j:log4j-core:2.17.1` to `2.17.2`
* Updated `org.apache.logging.log4j:log4j-slf4j18-impl:2.17.1` to `2.17.2`
* Updated `software.amazon.awssdk:cloudwatch:2.17.133` to `2.17.216`
* Updated `software.amazon.awssdk:secretsmanager:2.17.133` to `2.17.216`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.162` to `1.12.245`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.9` to `3.10`
* Updated `org.mockito:mockito-core:4.3.1` to `4.6.1`
* Updated `org.testcontainers:junit-jupiter:1.16.3` to `1.17.2`
* Updated `org.testcontainers:localstack:1.16.3` to `1.17.2`

### Plugin Dependency Updates

* Removed `com.exasol:artifact-reference-checker-maven-plugin:0.4.0`
* Updated `com.exasol:error-code-crawler-maven-plugin:1.1.0` to `1.1.1`
* Updated `com.exasol:project-keeper-maven-plugin:2.3.2` to `2.4.6`
* Removed `org.apache.maven.plugins:maven-assembly-plugin:3.3.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.9.0` to `3.10.1`
* Updated `org.apache.maven.plugins:maven-jar-plugin:3.2.0` to `2.4`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.8.1` to `2.10.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.7` to `0.8.8`
* Updated `org.sonatype.ossindex.maven:ossindex-maven-plugin:3.1.0` to `3.2.0`
