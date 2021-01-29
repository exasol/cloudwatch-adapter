# Requirements for the Cloudwatch-Adapter

This file describes the features and requirements for the Exasol – CloudWatch adapter (CWA).

## Features:

### Configuration

`feat~configuration~1`

CWA provides configuration options.

Needs: req

### Reliability

`feat~reliability~1`

The CWA reliably delivers metrics to CloudWatch.

That means:

* Each point is only reported once
* Each point is reported

Having both of these requirements is difficult in distributed systems. If not both are fully possible, the first one is more important.

Rationale:

Users will notice missing points in the CloudWatch console while they will not notice wrong data.

Needs: req

## Requirements

### Credentials

`req~secure-store-exasol-credentials~1`

CWA allows users to securely store the Exasol database credentials

Covers:

* [feat~configuration~1](#configuration)

Needs: dsn

### Configure Which Metrics the Adapter Transfers

`req~configure-transfered-metrics~1`

CWA allows users to define which metrics the adapter transfers to CloudWatch.

Covers:

* [feat~configuration~1](#configuration)

Needs: dsn

## At Most Once Reporting

`req~no-double-reporting~1`

CWA delivers each metric point at most once.

Rationale:

Reporting a metric multiple times changes the sum value of the statistics.

Covers:

* [feat~reliability~1](#Reliability)

Needs: dsn

## Avoid Loosing Data

`req~avoid-loosing-points~1`

CWA tries with best effort to report all points.

Covers:

* [feat~reliability~1](#Reliability)

Needs: dsn