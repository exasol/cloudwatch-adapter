# Design of the Exasol CloudWatch Adapter

## Metric Selection Using Environment Variable

`dsn~env-var-for-metrics-selection~1`

Users configure which metric the adapter should report using an environment variable that they can set in the Lambda configuration.

Even so the metrics are distributed over multiple system tables, a single name fits, since the column names are unique over all tables.

Covers:

* `req~configure-transfered-metrics~1`

Needs: impl, utest, itest

### Report Only Minute Before Event

`dsn~report-minute-before-event~1`

This adapter is triggered by a scheduled event of AWS EventBridge. This service guarantees not to invoke the adapter twice per minute.

For each invocation, that adapter reports the entries of the system tables from the minute before the event time.

So if for example the event time is 12:01, the adapter will report the data from with timestamps between 12:00 (inclusive) and 12:01 (exclusive).

By that the adapter makes sure, that no data is reported twice.

Covers:

* `req~no-double-reporting~1`

Needs: impl, utest, itest

## No Error Handling

`dsn~dont-handle-errors~1`

In this first implementation we decided not to implement any mechanisms that prevent the adapter from loosing points. In case the reporting fails for some reason (Exasol or CloudWatch not reachable), the adapter will simply abort and not retry.

Covers:

* `req~avoid-loosing-points~1`

Rationale:

We considered the following different solutions:

### Solution A: Retry if Lambda Execution Failed

AWS Lambda can write an entry to a dead letter queue and then retry the execution.

Pros:

* Simple
* Can scale / parallel running Lambdas are still possible

Cons:

* It's not possible to find out if some points were written and others not (Points are written in multiple API requests due to a limit per request).

### Solution B (Variant of A):

Split into two Lambdas. One that plans the publishing and one call per CloudWatch API request.

Pros:

* In case on CloudWatch API request fails, we can not resume
* Still parallel

Cons:

* It's probably not possible to transactional schedule Lambdas calls / emit event bridge events. --> If the first Lambdas crashes during the publishing, we have the same problems as in solution A.

### Solution C:

Use DynamoDB as a state store that stores the last successful transmission.

Pros:

* Could also sync last day at first start
* Would recover all errors

Cons:

* More complex
* Requires sequential execution
* A locking mechanism is needed to ensure that two Lambdas do not report the same data simultaneously.

### Store Credentials

`dsn~exasol-credentials-from-secrets-manager~1`

This adapter reads the credentials for the Exasol database from AWS Secrets Manager. By that they are stored securely.

Covers:

* `req~secure-store-exasol-credentials~1`

Needs: impl, itest
