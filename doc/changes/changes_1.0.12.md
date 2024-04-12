# Cloudwatch Adapter 1.0.12, released 2024-??-??

Code name: Fixed vulnerability CVE-2024-23080 in joda-time:joda-time:jar:2.10.8:compile

## Summary

This release fixes the following vulnerability:

### CVE-2024-23080 (CWE-476) in dependency `joda-time:joda-time:jar:2.10.8:compile`
Joda Time v2.12.5 was discovered to contain a NullPointerException via the component org.joda.time.format.PeriodFormat::wordBased(Locale).
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-23080?component-type=maven&component-name=joda-time%2Fjoda-time&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2024-23080
* https://github.com/advisories/GHSA-gxgx-2mvf-9gh5

## Security

* #99: Fixed vulnerability CVE-2024-23080 in dependency `joda-time:joda-time:jar:2.10.8:compile`

## Dependency Updates

### Compile Dependency Updates

* Updated `software.amazon.awssdk:cloudwatch:2.25.28` to `2.25.30`
* Updated `software.amazon.awssdk:secretsmanager:2.25.28` to `2.25.30`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.698` to `1.12.700`
