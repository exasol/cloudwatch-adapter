# Cloudwatch Adapter 2.0.0, released 2025-12-17

Code name: Fixed vulnerability CVE-2025-58056 in io.netty:netty-codec-http:jar:4.1.124.Final:runtime and CVE-2025-58057 in io.netty:netty-codec:jar:4.1.124.Final:runtime

## Summary

This release upgrades the Java version to Java 21 and fixes the following vulnerability:

### CVE-2025-58056 (CWE-444) in dependency `io.netty:netty-codec-http:jar:4.1.124.Final:runtime`
Netty is an asynchronous event-driven network application framework for development of maintainable high performance protocol servers and clients. In versions 4.1.124.Final, and 4.2.0.Alpha3 through 4.2.4.Final, Netty incorrectly accepts standalone newline characters (LF) as a chunk-size line terminator, regardless of a preceding carriage return (CR), instead of requiring CRLF per HTTP/1.1 standards. When combined with reverse proxies that parse LF differently (treating it as part of the chunk extension), attackers can craft requests that the proxy sees as one request but Netty processes as two, enabling request smuggling attacks. This is fixed in versions 4.1.125.Final and 4.2.5.Final.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2025-58056?component-type=maven&component-name=io.netty%2Fnetty-codec-http&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2025-58056
* https://github.com/netty/netty/security/advisories/GHSA-fghv-69vj-qj49

### CVE-2025-58057 (CWE-409) in dependency `io.netty:netty-codec:jar:4.1.124.Final:runtime`
netty-codec - Improper Handling of Highly Compressed Data (Data Amplification)
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2025-58057?component-type=maven&component-name=io.netty%2Fnetty-codec&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2025-58057
* https://github.com/netty/netty/security/advisories/GHSA-3p8m-j85q-pgmj

## Security

* #116: Fixed vulnerability CVE-2025-58056 in dependency `io.netty:netty-codec-http:jar:4.1.124.Final:runtime`
* #114: Fixed vulnerability CVE-2025-58057 in dependency `io.netty:netty-codec:jar:4.1.124.Final:runtime`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.amazonaws:aws-lambda-java-core:1.3.0` to `1.4.0`
* Updated `com.amazonaws:aws-lambda-java-events:3.15.0` to `3.16.1`
* Updated `com.exasol:error-reporting-java:1.0.1` to `1.0.2`
* Updated `com.exasol:exasol-jdbc:25.2.3` to `25.2.5`
* Updated `com.exasol:test-db-builder-java:3.6.1` to `3.6.4`
* Updated `org.apache.logging.log4j:log4j-api:2.24.3` to `2.25.3`
* Updated `org.apache.logging.log4j:log4j-core:2.24.3` to `2.25.3`
* Updated `org.apache.logging.log4j:log4j-slf4j-impl:2.24.3` to `2.25.3`
* Updated `software.amazon.awssdk:cloudwatch:2.31.54` to `2.40.10`
* Updated `software.amazon.awssdk:secretsmanager:2.31.54` to `2.40.10`

### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:7.1.7` to `7.2.2`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.19.4` to `4.2.5`
* Updated `org.junit.jupiter:junit-jupiter-api:5.13.0` to `6.0.1`
* Updated `org.junit.jupiter:junit-jupiter-params:5.13.0` to `6.0.1`
* Updated `org.mockito:mockito-core:5.18.0` to `5.21.0`
* Removed `org.testcontainers:junit-jupiter:1.21.1`
* Removed `org.testcontainers:localstack:1.21.1`
* Added `org.testcontainers:testcontainers-junit-jupiter:2.0.3`
* Added `org.testcontainers:testcontainers-localstack:2.0.3`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:2.0.4` to `2.0.5`
* Updated `com.exasol:project-keeper-maven-plugin:5.2.3` to `5.4.4`
* Updated `com.exasol:quality-summarizer-maven-plugin:0.2.0` to `0.2.1`
* Updated `io.github.git-commit-id:git-commit-id-maven-plugin:9.0.1` to `9.0.2`
* Updated `org.apache.maven.plugins:maven-artifact-plugin:3.6.0` to `3.6.1`
* Updated `org.apache.maven.plugins:maven-clean-plugin:3.4.1` to `3.5.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.14.0` to `3.14.1`
* Added `org.apache.maven.plugins:maven-dependency-plugin:3.9.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.5.0` to `3.6.2`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.5.3` to `3.5.4`
* Updated `org.apache.maven.plugins:maven-resources-plugin:3.3.1` to `3.4.0`
* Updated `org.apache.maven.plugins:maven-shade-plugin:3.6.0` to `3.6.1`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.5.3` to `3.5.4`
* Updated `org.codehaus.mojo:exec-maven-plugin:3.5.1` to `3.6.2`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.7.0` to `1.7.3`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.18.0` to `2.20.1`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.13` to `0.8.14`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:5.1.0.4751` to `5.5.0.6356`
