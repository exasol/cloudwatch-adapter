# Cloud-Watch Adapter Developers Guide

This guide contains information for developers of this adapter.

## Release

Release this adapter according to this [tutorial](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-template-publishing-applications.html).

Publishing of this repository will fail, due to a [bug in SAM](https://github.com/aws/serverless-application-model/issues/1931).

Workaround:

1. Remove the `RetryPolicy` from `sam/template.yaml`
1. Run `sam package` and `sam publish` (with appropriate paramters)
1. Add the `RetryPolicy` again to `sam/template.yaml`
1. Run `sam package` and `sam publish` again