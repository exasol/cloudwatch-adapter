# Cloud-Watch Adapter Developers Guide

This guide contains information for developers of this adapter.

## Manual Deployment via AWS SAM

You can also modify this adapter and deploy it directly.

1. [Install the AWS SAM cli](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html)
2. Build the lambda JAR by executing `mvn package -DskipTests`
3. If your Exasol database is accessible via the internet you don't need to configure subnet and security group. In this case edit `sam/template.yaml` (ensure to not commit your changes!)
   * Remove `VpcConfig` from the Lambda function
   * Remove parameters `SubnetId` and `SecurityGroup`
4. Go to the `sam/` directory and run `sam deploy --guided`
   * If the stack was already deployed and you have file `sam/samconfig.toml`, you can update the stack with `sam deploy`

## Manually Triggering the Lambda

The timestamp is required as payload for the lambda:

```json
{
  "time": "2021-12-01T11:05:53"
}
```

The timestamp must be between now (because of clock synchronization) and two weeks in the past (restriction for CloudWatch events).

You can invoke the lambda like this:

```sh
aws lambda invoke --function-name $lambda_name --invocation-type RequestResponse --log-type Tail --payload "$(echo '{"time": "2023-04-18T08:05:53"}' | base64)" output.json | jq --raw-output .LogResult | base64 --decode
```

## Release

Release this adapter according to this [tutorial](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-template-publishing-applications.html).

Steps:

* Update semantic version in `sam/template.yml`
* Make a release on GitHub
* Download packed SAM template from the `Prepare SAM release` GitHub action
* Send it to someone who can publish it in the `Exasol AG` AWS account