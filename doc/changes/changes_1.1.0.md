# Cloudwatch Adapter 1.1.0, released 2024-06-11

Code name: Support Exasol v8

## Summary

This release adds support for Exasol v8. It also enables [SnapStart](https://docs.aws.amazon.com/lambda/latest/dg/snapstart.html) for the AWS Lambda function to speedup execution and adds the `VCPU` (total number of virtual CPUs used by the Exasol cluster) as an additional metric for Exasol v8.

## Features

* #91: Added support for Exasol v8

## Dependency Updates

### Compile Dependency Updates

* Updated `com.amazonaws:aws-lambda-java-events:3.11.4` to `3.11.5`
* Updated `software.amazon.awssdk:cloudwatch:2.25.28` to `2.25.70`
* Updated `software.amazon.awssdk:secretsmanager:2.25.28` to `2.25.70`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.698` to `1.12.740`
* Updated `com.exasol:exasol-testcontainers:7.0.1` to `7.1.0`
* Updated `org.mockito:mockito-core:5.11.0` to `5.12.0`
* Updated `org.testcontainers:junit-jupiter:1.19.7` to `1.19.8`
* Updated `org.testcontainers:localstack:1.19.7` to `1.19.8`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:2.0.2` to `2.0.3`
* Updated `com.exasol:project-keeper-maven-plugin:4.3.0` to `4.3.3`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.4.1` to `3.5.0`
* Updated `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0` to `3.2.0`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.11.0.3922` to `4.0.0.4121`
