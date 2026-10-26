# Cloudwatch Adapter 2.0.3, released 2026-10-26

Code name: Fix 21 dependency vulnerabilities

## Summary

Addressed reported vulnerabilities in Netty, JSch, and Apache HttpClient dependencies.

- Fixes #136
- Fixes #138
- Fixes #140
- Fixes #142
- Fixes #143
- Fixes #144
- Fixes #146
- Fixes #147
- Fixes #148
- Fixes #150
- Fixes #151
- Fixes #152
- Fixes #153
- Fixes #157
- Fixes #158
- Fixes #159
- Fixes #160
- Fixes #161
- Fixes #162
- Fixes #163
- Fixes #164
- Fixes #165

## Security

* #142: Fixed CVE-2026-75595 (mutual TLS bypass) in dependency `io.netty:netty-handler:4.2.16.Final`
* #140: Fixed CVE-2026-75596 (algorithmic complexity) in dependency `io.netty:netty-handler:4.2.16.Final`
* #143: Fixed CVE-2026-62243 (TLS hostname verification) in dependency `io.netty:netty-handler:4.2.16.Final`
* #147: Fixed CVE-2026-89044 (HTTP request smuggling) in dependency `io.netty:netty-codec-http2:4.2.16.Final`
* #150: Fixed CVE-2026-93492 (resource exhaustion) in dependency `io.netty:netty-codec-http2:4.2.16.Final`
* #151: Fixed CVE-2026-93567 (improper input validation) in dependency `io.netty:netty-codec-http2:4.2.16.Final`
* #152: Fixed CVE-2026-93568 (improper input validation) in dependency `io.netty:netty-codec-http2:4.2.16.Final`
* #153: Fixed CVE-2026-93579 (resource exhaustion) in dependency `io.netty:netty-codec-http2:4.2.16.Final`
* #146: Fixed CVE-2026-86231 (certificate expiration validation) in dependency `com.github.mwiede:jsch:2.28.5`
* #136: Fixed CVE-2026-59903 (sensitive information caching) in dependency `io.netty:netty-codec-http:4.2.16.Final`
* #148: Fixed CVE-2026-89044 (HTTP request smuggling) in dependency `io.netty:netty-codec-http:4.2.16.Final`
* #158: Fixed CVE-2026-93488 (resource exhaustion) in dependency `io.netty:netty-codec-http:4.2.16.Final`
* #159: Fixed CVE-2026-93491 (resource exhaustion) in dependency `io.netty:netty-codec-http:4.2.16.Final`
* #160: Fixed CVE-2026-93558 (resource exhaustion) in dependency `io.netty:netty-codec-http:4.2.16.Final`
* #161: Fixed CVE-2026-93562 (HTTP request smuggling) in dependency `io.netty:netty-codec-http:4.2.16.Final`
* #162: Fixed CVE-2026-93565 (RTSP method smuggling) in dependency `io.netty:netty-codec-http:4.2.16.Final`
* #163: Fixed CVE-2026-93574 (HTTP request smuggling) in dependency `io.netty:netty-codec-http:4.2.16.Final`
* #164: Fixed CVE-2026-93566 (HTTP request smuggling) in dependency `io.netty:netty-codec-http:4.2.16.Final`
* #165: Fixed CVE-2026-93573 (HTTP interpretation conflict) in dependency `io.netty:netty-codec-http:4.2.16.Final`
* #157: Fixed CVE-2026-100666 (HTTP response splitting) in dependency `io.netty:netty-codec-http:4.2.16.Final`
* #138: Fixed CVE-2026-64607 (connection resource leak) in dependency `org.apache.httpcomponents.client5:httpclient5:5.6.2`
* #144: Fixed CVE-2026-71290 (TLS certificate validation) in dependency `org.apache.httpcomponents.client5:httpclient5:5.6.2`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.amazonaws:aws-lambda-java-core:1.4.0` to `1.4.1`
* Updated `com.amazonaws:aws-lambda-java-log4j2:1.6.4` to `1.6.5`
* Updated `com.exasol:exasol-jdbc:26.2.8` to `26.2.10`
* Updated `com.exasol:test-db-builder-java:4.0.2` to `4.0.3`
* Updated `software.amazon.awssdk:cloudwatch:2.49.5` to `2.57.3`
* Updated `software.amazon.awssdk:secretsmanager:2.49.5` to `2.57.3`

### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:8.0.1` to `8.0.2`
* Updated `io.floci:testcontainers-floci:2.13.0` to `2.17.0`
* Updated `nl.jqno.equalsverifier:equalsverifier:4.5` to `4.5.2`
* Updated `org.junit.jupiter:junit-jupiter:6.1.2` to `6.1.3`
* Updated `org.mockito:mockito-core:5.23.0` to `5.24.0`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:2.1.0` to `2.1.2`
* Updated `com.exasol:project-keeper-maven-plugin:5.7.4` to `5.8.1`
* Updated `io.github.git-commit-id:git-commit-id-maven-plugin:10.0.0` to `10.0.1`
* Updated `org.apache.maven.plugins:maven-artifact-plugin:3.6.1` to `3.7.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.15.0` to `3.16.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.5.6` to `3.6.0`
* Updated `org.apache.maven.plugins:maven-install-plugin:3.1.4` to `3.2.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.5.6` to `3.6.0`
* Updated `org.apache.maven.plugins:maven-toolchains-plugin:3.2.0` to `3.3.0`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.7.3` to `1.8.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.21.0` to `2.23.0`
* Updated `org.itsallcode:openfasttrace-maven-plugin:2.3.1` to `3.1.0`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:5.7.0.6970` to `5.8.0.7211`
