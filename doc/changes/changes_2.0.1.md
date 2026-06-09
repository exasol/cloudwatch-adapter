# Cloudwatch Adapter 2.0.1, released 2026-06-09

Code name: Fix 16 vulnerabilities in dependencies

## Summary

This release fixes the following vulnerabilities in dependencies:

* `io.netty:netty-transport-classes-epoll:jar:4.2.9.Final:runtime`:
  * CVE-2026-42577 (CWE-772): Missing Release of Resource after Effective Lifetime
* `io.netty:netty-codec-compression:jar:4.2.9.Final:runtime`
  * CVE-2026-42583 (CWE-400): Uncontrolled Resource Consumption ('Resource Exhaustion')
* `io.netty:netty-codec-http:jar:4.2.9.Final:runtime`:
  * CVE-2026-33870 (CWE-444): Inconsistent Interpretation of HTTP Requests ('HTTP Request Smuggling')
  * CVE-2026-41417 (CWE-444): Inconsistent Interpretation of HTTP Requests ('HTTP Request Smuggling')
  * CVE-2026-42580 (CWE-190): Integer Overflow or Wraparound
  * CVE-2026-42581 (CWE-444): Inconsistent Interpretation of HTTP Requests ('HTTP Request Smuggling')
  * CVE-2026-42584 (CWE-444): Inconsistent Interpretation of HTTP Requests ('HTTP Request Smuggling')
  * CVE-2026-42585 (CWE-444): Inconsistent Interpretation of HTTP Requests ('HTTP Request Smuggling')
  * CVE-2026-42587 (CWE-400): Uncontrolled Resource Consumption ('Resource Exhaustion')
* `org.apache.logging.log4j:log4j-core:jar:2.25.3:compile`
  * CVE-2026-34477 (CWE-295): Improper Certificate Validation
  * CVE-2026-34478 (CWE-117): Improper Output Neutralization for Logs
  * CVE-2026-34479 (CWE-116): Improper Encoding or Escaping of Output
  * CVE-2026-34480 (CWE-116): Improper Encoding or Escaping of Output
* `io.netty:netty-codec-http2:jar:4.2.9.Final:runtime`
  * CVE-2026-33871 (CWE-770): Allocation of Resources Without Limits or Throttling
  * CVE-2026-42587 (CWE-400): Uncontrolled Resource Consumption ('Resource Exhaustion')
  * CVE-2026-48043 (CWE-400): Uncontrolled Resource Consumption ('Resource Exhaustion')

## Security

* #118: Fixed 16 vulnerabilities in dependencies

## Dependency Updates

### Compile Dependency Updates

* Updated `com.amazonaws:aws-lambda-java-log4j2:1.6.0` to `1.6.4`
* Updated `com.exasol:exasol-jdbc:25.2.5` to `26.2.7`
* Updated `com.exasol:test-db-builder-java:3.6.4` to `4.0.0`
* Updated `org.apache.logging.log4j:log4j-api:2.25.3` to `2.26.0`
* Updated `org.apache.logging.log4j:log4j-core:2.25.3` to `2.26.0`
* Updated `org.apache.logging.log4j:log4j-slf4j-impl:2.25.3` to `2.26.0`
* Updated `org.itsallcode:junit5-system-extensions:1.2.2` to `1.2.3`
* Updated `software.amazon.awssdk:cloudwatch:2.40.5` to `2.46.6`
* Updated `software.amazon.awssdk:secretsmanager:2.40.5` to `2.46.6`

### Runtime Dependency Updates

* Updated `org.eclipse.parsson:parsson:1.1.7` to `1.1.9`

### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:7.2.2` to `7.3.0`
* Added `io.floci:testcontainers-floci:2.9.0`
* Updated `nl.jqno.equalsverifier:equalsverifier:4.2.5` to `4.5`
* Removed `org.junit.jupiter:junit-jupiter-api:6.0.1`
* Removed `org.junit.jupiter:junit-jupiter-params:6.0.1`
* Added `org.junit.jupiter:junit-jupiter:6.1.0`
* Updated `org.mockito:mockito-core:5.21.0` to `5.23.0`
* Updated `org.testcontainers:testcontainers-junit-jupiter:2.0.3` to `2.0.5`
* Removed `org.testcontainers:testcontainers-localstack:2.0.3`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:2.0.5` to `2.0.7`
* Updated `com.exasol:project-keeper-maven-plugin:5.4.4` to `5.6.2`
* Updated `io.github.git-commit-id:git-commit-id-maven-plugin:9.0.2` to `10.0.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.14.1` to `3.15.0`
* Updated `org.apache.maven.plugins:maven-dependency-plugin:3.9.0` to `3.10.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.5.4` to `3.5.5`
* Updated `org.apache.maven.plugins:maven-resources-plugin:3.4.0` to `3.5.0`
* Updated `org.apache.maven.plugins:maven-shade-plugin:3.6.1` to `3.6.2`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.5.4` to `3.5.5`
* Updated `org.codehaus.mojo:exec-maven-plugin:3.6.2` to `3.6.3`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.20.1` to `2.21.0`
