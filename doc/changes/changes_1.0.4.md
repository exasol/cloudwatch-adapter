# cloudwatch-adapter 1.0.4, released 2022-08-03

Code name: Fix vulnerabilities in test dependencies

## Summary

This release fixes the following vulnerability in test dependency:

* com.amazonaws:aws-java-sdk-s3:jar:1.12.245:test
  * CVE-2022-31159, severity CWE-22: Improper Limitation of a Pathname to a Restricted Directory ('Path Traversal') (6.5)

## Bugfixes

* #77: Fixed vulnerabilities in dependencies

## Dependency Updates

### Compile Dependency Updates

* Updated `org.apache.logging.log4j:log4j-api:2.17.2` to `2.18.0`
* Updated `org.apache.logging.log4j:log4j-core:2.17.2` to `2.18.0`
* Updated `org.apache.logging.log4j:log4j-slf4j18-impl:2.17.2` to `2.18.0`
* Updated `software.amazon.awssdk:cloudwatch:2.17.217` to `2.17.245`
* Updated `software.amazon.awssdk:secretsmanager:2.17.217` to `2.17.245`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.245` to `1.12.274`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.10` to `3.10.1`
* Updated `org.junit.jupiter:junit-jupiter:5.8.2` to `5.9.0`
* Updated `org.testcontainers:junit-jupiter:1.17.2` to `1.17.3`
* Updated `org.testcontainers:localstack:1.17.2` to `1.17.3`

### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:2.4.6` to `2.5.0`
* Updated `org.itsallcode:openfasttrace-maven-plugin:1.4.0` to `1.5.0`
