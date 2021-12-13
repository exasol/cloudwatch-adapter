# cloudwatch-adapter 0.1.2, released 2021-12-13

Code name: Important fix for CVE-2021-44228

## Summary

In this release we updated log4j dependencies and by that fixed the transitive CVE-2021-44228.

Since this connector does not expose any public interface, we could identify no attack scenario. However, please update to the latest version to make sure that your system is safe.

## Dependency Updates

### Compile Dependency Updates

* Updated `com.amazonaws:aws-lambda-java-log4j2:1.2.0` to `1.3.0`
* Updated `org.apache.logging.log4j:log4j-api:2.14.1` to `2.15.0`
* Updated `org.apache.logging.log4j:log4j-core:2.14.1` to `2.15.0`
* Updated `org.apache.logging.log4j:log4j-slf4j18-impl:2.14.1` to `2.15.0`
