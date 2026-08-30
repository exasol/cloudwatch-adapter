# Cloudwatch Adapter 2.0.3, released 2026-??-??

Code name: Fixed vulnerabilities CVE-2026-75595, CVE-2026-62243, CVE-2026-71290

## Summary

This release fixes the following 3 vulnerabilities:

### CVE-2026-75595 (CWE-754) in dependency `io.netty:netty-handler:jar:4.2.16.Final:runtime`
Netty is an asynchronous, event-driven network application framework. Prior to 4.1.137.Fina and 4.2.17.Final, io.netty.handler.ssl.SslClientHelloHandler#decode checks the wrong offset before reading the four-byte TLS handshake header, so a ClientHello whose handshake header spans records can cause an IndexOutOfBoundsException and invoke select(ctx, null). This selects the default SslContext instead of the SNI-specific context. In deployments where per-SNI clientAuth=REQUIRE is the sole mutual TLS gate, the default SslContext uses clientAuth=NONE or clientAuth=OPTIONAL, and no application-layer certificate verification exists, an unauthenticated remote attacker can bypass the protected route's mutual TLS requirement. This issue is fixed in versions 4.1.137.Final and 4.2.17.Final.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-75595?component-type=maven&component-name=io.netty%2Fnetty-handler&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-75595
* https://github.com/netty/netty/pull/17213
* https://github.com/netty/netty/pull/17217
* https://github.com/netty/netty/security/advisories/GHSA-c4c3-7fpv-j4q5

### CVE-2026-62243 (CWE-297) in dependency `io.netty:netty-handler:jar:4.2.16.Final:runtime`
io.netty:netty-handler - Improper Validation of Certificate with Host Mismatch
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-62243?component-type=maven&component-name=io.netty%2Fnetty-handler&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-62243
* https://github.com/netty/netty/security/advisories/GHSA-p85m-gvr3-788c

### CVE-2026-71290 (CWE-295) in dependency `org.apache.httpcomponents.client5:httpclient5:jar:5.6.2:runtime`
Improper TLS hostname verification vulnerability in Apache HttpComponents Client 5.4 or newer.Â HostnameVerificationPolicy#BUILTIN setting has no effect when used with the async version of HttpClient. An attacker that can intercept and modify traffic between the client and the server can impersonate the server by presenting a valid certificate for a different domain.Â 

Please note the classic version of HttpClient is not affected by this vulnerability.Â 

Affected users are recommended to upgrade to at least version 5.6.4, which fixes the issue.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-71290?component-type=maven&component-name=org.apache.httpcomponents.client5%2Fhttpclient5&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-71290
* https://lists.apache.org/thread/bhf7g2zwpom2ohvwjjjlonc93br2s8vq
* https://github.com/advisories/GHSA-72q8-9rgw-5g6j

## Security

* #142: Fixed vulnerability CVE-2026-75595 in dependency `io.netty:netty-handler:jar:4.2.16.Final:runtime`
* #143: Fixed vulnerability CVE-2026-62243 in dependency `io.netty:netty-handler:jar:4.2.16.Final:runtime`
* #144: Fixed vulnerability CVE-2026-71290 in dependency `org.apache.httpcomponents.client5:httpclient5:jar:5.6.2:runtime`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.amazonaws:aws-lambda-java-log4j2:1.6.4` to `1.6.5`
* Updated `software.amazon.awssdk:cloudwatch:2.49.5` to `2.54.7`
* Updated `software.amazon.awssdk:secretsmanager:2.49.5` to `2.54.7`

### Test Dependency Updates

* Updated `io.floci:testcontainers-floci:2.13.0` to `2.15.0`
* Updated `nl.jqno.equalsverifier:equalsverifier:4.5` to `4.5.2`
* Updated `org.junit.jupiter:junit-jupiter:6.1.2` to `6.1.3`
