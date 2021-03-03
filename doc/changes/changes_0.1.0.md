# CloudWatch-adapter 0.1.0, released 2021-03-03

Code name: Initial release

## Summary

This is the very first release of the Exasol – CloudWatch adapter.

Known issue:

* In case your Exasol database uses a timezone with time-shift as `DBTIMEZONE`, this adapter will **not report** the hour when the time is shifted back, since Exasol stores the statistics entries in the `DBTIMEZONE` and by that, the log entries are ambiguous in that hour (see [#2](https://github.com/exasol/cloudwatch-adapter/issues/2)).
* Due to a bug in SAM we can not publish the app with a retry count set to 0. This can lead to duplicate reported data if the Lambda fails after reporting some points and EventBridge triggers it again (see [#21](https://github.com/exasol/cloudwatch-adapter/issues/21)).

## Features / Enhancements

* #1: Implemented basic adapter
* #3: Added SAM deployment
* #4: Use AWS Secrets Manager for storing the Exasol credentials
* #7: Added additional metrics: `NET`, `SWAP`, `DB_RAM_SIZE`, `NODES`
* #30: Added configuration for VPC
* #35: Added regular reporting of metrics from EXA_SYSTEM_EVENTS
* #37: Report metrics from EXA_SYSTEM_EVENTS only every 4 minutes
* #31: Added wrapper for AWS SDK exceptions

## Documentation

* #28: Added documentation on VPC
* #22: Added a guide for manual deployment
* #33: Added documentation on VPC API endpoints

## Refactoring

* #10: Changed integration tests to use localstack instead of online API
* #12: Changed localstack version from latest to 0.12.6
* #39: Refactored the exasol metric class structure

## Bugfixes

* #8: Fixed wrong metrics
* #14: Removed failing SAM validate CI, since it requires AWS access
* #19: Fixed SAM template (`ClassNotFoundException` on deployment)
