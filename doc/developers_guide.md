# Cloud-Watch Adapter Developers Guide

This guide contains information for developers of this adapter.

## Manually Triggering the Lambda

The timestamp is required as payload for the lambda:

```json
{
  "time": "2021-12-01T11:05:53"
}
```

The timestamp must be between now (because of clock synchronisation) and two weeks in the past (restriction for CloudWatch events).

You can invoke the lambda like this:

```sh
aws lambda invoke --function-name $lambda_name --invocation-type RequestResponse --log-type Tail --payload "$(echo '{"time": "2022-02-22T10:05:53"}' | base64)" output.json | jq --raw-output .LogResult | base64 --decode
```

## Release

Release this adapter according to this [tutorial](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-template-publishing-applications.html).

Steps:

* Update semantic version in `sam/template.yml`
* Make a release on GitHub
* Download packed SAM template from the `Prepare SAM release` GitHub action
* Send it to someone who can publish it in the `Exasol AG` AWS account