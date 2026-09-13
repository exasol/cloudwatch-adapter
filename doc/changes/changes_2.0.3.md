# Cloudwatch Adapter 2.0.3, released 2026-??-??

Code name: Fixed vulnerabilities CVE-2026-86231, CVE-2026-89044, CVE-2026-89044

## Summary

This release fixes the following 3 vulnerabilities:

### CVE-2026-86231 (CWE-298) in dependency `com.github.mwiede:jsch:jar:2.28.5:test`
A security flaw has been discovered in mwiede jsch up to 2.28.5. Affected is the function getRevokedKeys of the file src/main/java/com/jcraft/jsch/KnownHosts.java. Performing a manipulation of the argument known_hosts results in improper check for certificate revocation. The attack is possible to be carried out remotely. The attack is considered to have high complexity. The exploitability is told to be difficult. The exploit has been released to the public and may be used for attacks. Upgrading to version 2.28.6 is able to address this issue. The patch is named 194a2f76a5c0f1c3f778565be3fd66bcafc42d23. You should upgrade the affected component.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-86231?component-type=maven&component-name=com.github.mwiede%2Fjsch&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-86231
* https://github.com/mwiede/jsch/issues/1091
* https://github.com/mwiede/jsch/pull/1098
* https://github.com/mwiede/jsch/releases/tag/jsch-2.28.6

### CVE-2026-89044 (CWE-444) in dependency `io.netty:netty-codec-http2:jar:4.2.16.Final:runtime`
Netty versions 4.1.133.Final through 4.1.137.Final and 4.2.13.Final through 4.2.17.Final fail to properly validate the final transfer coding in the Transfer-Encoding header, allowing attackers to smuggle requests by using malformed encoding declarations. Attackers can split Transfer-Encoding headers across multiple lines or use values like 'chunked, xchunked' to bypass validation and decode messages as chunked when the final coding is not chunked, enabling request smuggling attacks.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-89044?component-type=maven&component-name=io.netty%2Fnetty-codec-http2&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-89044
* https://github.com/netty/netty/releases/tag/netty-4.1.138.Final
* https://github.com/netty/netty/releases/tag/netty-4.2.18.Final
* https://github.com/netty/netty/security/advisories/GHSA-hcvj-94mj-jp5c

### CVE-2026-89044 (CWE-444) in dependency `io.netty:netty-codec-http:jar:4.2.16.Final:runtime`
Netty versions 4.1.133.Final through 4.1.137.Final and 4.2.13.Final through 4.2.17.Final fail to properly validate the final transfer coding in the Transfer-Encoding header, allowing attackers to smuggle requests by using malformed encoding declarations. Attackers can split Transfer-Encoding headers across multiple lines or use values like 'chunked, xchunked' to bypass validation and decode messages as chunked when the final coding is not chunked, enabling request smuggling attacks.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-89044?component-type=maven&component-name=io.netty%2Fnetty-codec-http&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-89044
* https://github.com/netty/netty/releases/tag/netty-4.1.138.Final
* https://github.com/netty/netty/releases/tag/netty-4.2.18.Final
* https://github.com/netty/netty/security/advisories/GHSA-hcvj-94mj-jp5c

## Security

* #146: Fixed vulnerability CVE-2026-86231 in dependency `com.github.mwiede:jsch:jar:2.28.5:test`
* #147: Fixed vulnerability CVE-2026-89044 in dependency `io.netty:netty-codec-http2:jar:4.2.16.Final:runtime`
* #148: Fixed vulnerability CVE-2026-89044 in dependency `io.netty:netty-codec-http:jar:4.2.16.Final:runtime`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.amazonaws:aws-lambda-java-log4j2:1.6.4` to `1.6.5`
* Updated `com.exasol:exasol-jdbc:26.2.8` to `26.2.9`
* Updated `software.amazon.awssdk:cloudwatch:2.49.5` to `2.54.17`
* Updated `software.amazon.awssdk:secretsmanager:2.49.5` to `2.54.17`

### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:8.0.1` to `8.0.2`
* Updated `io.floci:testcontainers-floci:2.13.0` to `2.16.1`
* Updated `nl.jqno.equalsverifier:equalsverifier:4.5` to `4.5.2`
* Updated `org.junit.jupiter:junit-jupiter:6.1.2` to `6.1.3`

### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:5.7.4` to `5.7.5`
