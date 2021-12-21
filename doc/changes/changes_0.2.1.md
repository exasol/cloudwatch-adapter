# cloudwatch-adapter 0.2.1, released 2021-12-21

Code name: Important fix for CVE-2021-45105

## Summary

In this release we updated log4j dependencies and by that fixed the transitive CVE-2021-45105.

Since this connector does not expose any public interface, we could identify no attack scenario. However, please update to the latest version to make sure that your system is safe.

## Dependency Updates

### Compile Dependency Updates

* Updated `org.apache.logging.log4j:log4j-api:2.16.0` to `2.17.0`
* Updated `org.apache.logging.log4j:log4j-core:2.16.0` to `2.17.0`
* Updated `org.apache.logging.log4j:log4j-slf4j18-impl:2.16.0` to `2.17.0`
