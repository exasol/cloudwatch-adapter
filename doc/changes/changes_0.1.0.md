#cloudwatch-adapter 0.1.0, released 2021-01-??

## Code name:

## Features / Enhancements

* #1: Implemented basic adapter
* #3: Added SAM deployment
* #4: Use AWS Secrets Manager for storing the Exasol credentials
* #7: Added additional metrics: `NET`, `SWAP`, `DB_RAM_SIZE`, `NODES`
* #30: Added configuration for VPC
* #35: Added regular reporting of metrics from EXA_SYSTEM_EVENTS

## Documentation

* #28: Added documentation on VPC
* #22: Added a guide for manual deployment
* #33: Added documentation on VPC API endpoints

## Refactoring

* #11: Changed integration tests to use localstack instead of online API
* #12: Changed localstack version from latest to 0.12.6
* #39: Refactored the exasol metric class structure

## Bugfixes

* #8: Fixed wrong metrics
* #14: Removed failing SAM validate CI, since it requires AWS access
* #19: Fixed SAM template (`ClassNotFoundException` on deployment)
