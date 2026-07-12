# Cloudwatch Adapter 2.0.2, released 2026-??-??

Code name: Fixed vulnerabilities CVE-2026-49844, CVE-2026-54399, CVE-2026-54428

## Summary

This release fixes the following 3 vulnerabilities:

### CVE-2026-49844 (CWE-116) in dependency `org.apache.logging.log4j:log4j-api:jar:2.26.0:compile`
Improper encoding of non-finite floating-point values during MapMessage JSON serialization in Apache Log4j API produces output that is not valid JSON. This issue affects Apache Log4j API versions 2.13.1 through 2.25.4 and version 2.26.0.

The fix for CVE-2026-34481 did not cover all code paths: when a MapMessage contains a non-finite IEEE 754 value (NaN, Infinity, or -Infinity), MapMessage.asJson() emits the corresponding bare token. RFC 8259 does not permit these tokens, so a conformant parser rejects the resulting document.

The defect is reachable only when both of the following conditions hold:

  *  The application uses the  message resolver https://logging.apache.org/log4j/2.x/manual/json-template-layout.html#event-template-resolver-message  of JsonTemplateLayout or any other layout that relies on MapMessage.asJson() or MapMessage.getFormattedMessage(new String[]{"JSON"}).
  *  The application logs a MapMessage that contains an attacker-controlled floating-point value.

An attacker who can supply a non-finite value can cause the affected layout to emit malformed JSON, which may corrupt the enclosing log record or disrupt downstream log ingestion and parsing.

Users are advised to upgrade to Apache Log4j API 2.25.5 or 2.26.1, both of which emit RFC 8259-compliant JSON for non-finite values.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-49844?component-type=maven&component-name=org.apache.logging.log4j%2Flog4j-api&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-49844
* https://logging.apache.org/security.html#CVE-2026-49844

### CVE-2026-54399 (CWE-400) in dependency `org.apache.httpcomponents.core5:httpcore5:jar:5.4.2:runtime`
Uncontrolled Resource Consumption vulnerability in the HTTP/1.1 message parserÂ in Apache HttpComponents Core (5.4.2 and earlier, 5.5-beta1 and earlier) allowsÂ an remote attacker to cause a denial of service through memory exhaustion by sending messages with excessive number of headers / excessive header length

Sonatype's research suggests that this CVE's details differ from those defined at NVD. See https://guide.sonatype.com/vulnerability/CVE-2026-54399 for details
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-54399?component-type=maven&component-name=org.apache.httpcomponents.core5%2Fhttpcore5&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-54399
* https://lists.apache.org/thread/zmxh1pl2zohov5ntdh4lt85gfrlchgpy
* http://www.openwall.com/lists/oss-security/2026/07/01/4

### CVE-2026-54428 (CWE-400) in dependency `org.apache.httpcomponents.core5:httpcore5-h2:jar:5.4:runtime`
Allocation of resources without limits or throttling in the HTTP/2 HPACK decoder in Apache HttpComponents Core (5.4.2 and earlier, 5.5-beta1 and earlier) allows an remote attacker to cause a denial of service through memory exhaustion by sending oversized compressed header blocks before the HTTP/2 SETTINGS acknowledgement causes the configured header list size limit to be applied.
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-54428?component-type=maven&component-name=org.apache.httpcomponents.core5%2Fhttpcore5-h2&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-54428
* https://lists.apache.org/thread/5zjp8vczvxq19pw2rvhs21q446bhl0sd

## Security

* #120: Fixed vulnerability CVE-2026-49844 in dependency `org.apache.logging.log4j:log4j-api:jar:2.26.0:compile`
* #121: Fixed vulnerability CVE-2026-54399 in dependency `org.apache.httpcomponents.core5:httpcore5:jar:5.4.2:runtime`
* #122: Fixed vulnerability CVE-2026-54428 in dependency `org.apache.httpcomponents.core5:httpcore5-h2:jar:5.4:runtime`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:26.2.7` to `26.2.8`
* Updated `com.exasol:test-db-builder-java:4.0.0` to `4.0.1`
* Updated `org.apache.logging.log4j:log4j-api:2.26.0` to `2.26.1`
* Updated `org.apache.logging.log4j:log4j-core:2.26.0` to `2.26.1`
* Updated `org.apache.logging.log4j:log4j-slf4j-impl:2.26.0` to `2.26.1`
* Updated `software.amazon.awssdk:cloudwatch:2.46.6` to `2.47.5`
* Updated `software.amazon.awssdk:secretsmanager:2.46.6` to `2.47.5`

### Test Dependency Updates

* Updated `io.floci:testcontainers-floci:2.9.0` to `2.11.0`
* Updated `org.junit.jupiter:junit-jupiter:6.1.0` to `6.1.2`

### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:5.6.2` to `5.7.3`
