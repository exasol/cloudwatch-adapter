#cloudwatch-adapter 0.1.0, released 2021-01-??

## Code name:

## Features / Enhancements

* #1: Implemented basic adapter
* #3: Added SAM deployment
* #4: Use AWS Secrets Manager for storing the Exasol credentials

## Refactoring

* #11: Changed integration tests to use localstack instead of online API

## Bugfixes

* #8: Fixed wrong metrics
* #14: Removed failing SAM validate CI, since it requires AWS access
* #19: Fixed SAM template (`ClassNotFoundException` on deployment)
