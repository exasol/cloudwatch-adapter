# Requirements for the Cloudwatch-Adapter

## Features:

### Configuration

`feat~configuration~1`

Users can configure this adapter intuitively.

Needs: req

### Consistency

`feat~consistency~1`

The adapter should never ever produce wrong data in CloudWatch. As far as possible it should transfer all available data. (Both is impossible).

Rationale:

Users will notice missing points in the CloudWatch console while they will not notice wrong data.

Needs: req

## Requirements

### Credentials

`req~secure-store-exasol-credentials~1`

Users want the credentials of their Exasol database to be stored securely.

Covers:

* [feat~configuration~1](#configuration)

Needs: dsn

### Configure Which Metrics the Adapter Transfers

`req~configure-transfered-metrics~1`

Users want to define which metrics to report.

Covers:

* [feat~configuration~1](#configuration)

Needs: dsn

## No-Double-Reporting

`req~no-double-reporting~1`

Since Cloudwatch aggregates the points to statistics, no point must be reported twice. Reporting a point multiple times would change the value of the sum statistic.

Covers:

* [feat~consistency~1](#Consistency)

Needs: dsn

## Avoid loosing data

`req~avoid-loosing-points~1`

Since Cloudwatch aggregates the points to statistics, no point must be reported twice. Reporting a point multiple times would change the value of the sum statistic.

Covers:

* [feat~consistency~1](#Consistency)

Needs: dsn