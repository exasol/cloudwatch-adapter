# Cloudwatch Adapter 1.1.1, released 2024-11-18

Code name: Fixed vulnerability CVE-2024-47535 in io.netty:netty-common:jar:4.1.108.Final:runtime

## Summary

This release fixes the following vulnerability:

### CVE-2024-47535 (CWE-400) in dependency `io.netty:netty-common:jar:4.1.108.Final:runtime`
Netty is an asynchronous event-driven network application framework for rapid development of maintainable high performance protocol servers & clients. An unsafe reading of environment file could potentially cause a denial of service in Netty. When loaded on an Windows application, Netty attempts to load a file that does not exist. If an attacker creates such a large file, the Netty application crashes. This vulnerability is fixed in 4.1.115.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-47535?component-type=maven&component-name=io.netty%2Fnetty-common&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2024-47535
* https://github.com/advisories/GHSA-xq3w-v528-46rv

## Security

* #102: Fixed vulnerability CVE-2024-47535 in dependency `io.netty:netty-common:jar:4.1.108.Final:runtime`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.amazonaws:aws-lambda-java-events:3.11.5` to `3.14.0`
* Updated `com.exasol:exasol-jdbc:24.1.0` to `24.2.0`
* Updated `com.exasol:test-db-builder-java:3.5.4` to `3.6.0`
* Updated `org.apache.logging.log4j:log4j-api:2.23.1` to `2.24.1`
* Updated `org.apache.logging.log4j:log4j-core:2.23.1` to `2.24.1`
* Updated `org.apache.logging.log4j:log4j-slf4j-impl:2.23.1` to `2.24.1`
* Updated `org.itsallcode:junit5-system-extensions:1.2.0` to `1.2.2`
* Updated `software.amazon.awssdk:cloudwatch:2.25.70` to `2.29.15`
* Updated `software.amazon.awssdk:secretsmanager:2.25.70` to `2.29.15`

### Runtime Dependency Updates

* Updated `org.eclipse.parsson:parsson:1.1.6` to `1.1.7`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.740` to `1.12.778`
* Updated `com.exasol:exasol-testcontainers:7.1.0` to `7.1.1`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.16.1` to `3.17.3`
* Updated `org.hamcrest:hamcrest:2.2` to `3.0`
* Updated `org.junit.jupiter:junit-jupiter:5.10.2` to `5.11.3`
* Updated `org.mockito:mockito-core:5.12.0` to `5.14.2`
* Updated `org.testcontainers:junit-jupiter:1.19.8` to `1.20.3`
* Updated `org.testcontainers:localstack:1.19.8` to `1.20.3`

### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:4.3.3` to `4.4.0`
* Added `com.exasol:quality-summarizer-maven-plugin:0.2.0`
* Updated `io.github.zlika:reproducible-build-maven-plugin:0.16` to `0.17`
* Updated `org.apache.maven.plugins:maven-clean-plugin:2.5` to `3.4.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.2.5` to `3.5.1`
* Updated `org.apache.maven.plugins:maven-install-plugin:2.4` to `3.1.3`
* Updated `org.apache.maven.plugins:maven-resources-plugin:2.6` to `3.3.1`
* Updated `org.apache.maven.plugins:maven-site-plugin:3.3` to `3.9.1`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.5` to `3.5.1`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.16.2` to `2.17.1`
