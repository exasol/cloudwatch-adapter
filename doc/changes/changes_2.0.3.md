# Cloudwatch Adapter 2.0.3, released 2026-??-??

Code name: Fixed vulnerability CVE-2026-75596 in io.netty:netty-handler:jar:4.2.16.Final:runtime

## Summary

This release fixes the following vulnerability:

### CVE-2026-75596 (CWE-407) in dependency `io.netty:netty-handler:jar:4.2.16.Final:runtime`
io.netty:netty-handler - Inefficient Algorithmic Complexity
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-75596?component-type=maven&component-name=io.netty%2Fnetty-handler&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-75596
* https://github.com/netty/netty/security/advisories/GHSA-fccg-mwvh-qqg4

## Security

* #140: Fixed vulnerability CVE-2026-75596 in dependency `io.netty:netty-handler:jar:4.2.16.Final:runtime`

## Dependency Updates

### Compile Dependency Updates

* Updated `software.amazon.awssdk:cloudwatch:2.49.5` to `2.54.2`
* Updated `software.amazon.awssdk:secretsmanager:2.49.5` to `2.54.2`

### Test Dependency Updates

* Updated `io.floci:testcontainers-floci:2.13.0` to `2.15.0`
* Updated `nl.jqno.equalsverifier:equalsverifier:4.5` to `4.5.1`
* Updated `org.junit.jupiter:junit-jupiter:6.1.2` to `6.1.3`
