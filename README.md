# Exasol – AWS Cloudwatch Adapter

[![Build Status](https://github.com/exasol/cloudwatch-adapter/actions/workflows/ci-build.yml/badge.svg)](https://github.com/exasol/cloudwatch-adapter/actions/workflows/ci-build.yml)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Acloudwatch-adapter&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.exasol%3Acloudwatch-adapter)

[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Acloudwatch-adapter&metric=security_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Acloudwatch-adapter)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Acloudwatch-adapter&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Acloudwatch-adapter)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Acloudwatch-adapter&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Acloudwatch-adapter)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Acloudwatch-adapter&metric=sqale_index)](https://sonarcloud.io/dashboard?id=com.exasol%3Acloudwatch-adapter)

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Acloudwatch-adapter&metric=code_smells)](https://sonarcloud.io/dashboard?id=com.exasol%3Acloudwatch-adapter)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Acloudwatch-adapter&metric=coverage)](https://sonarcloud.io/dashboard?id=com.exasol%3Acloudwatch-adapter)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Acloudwatch-adapter&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=com.exasol%3Acloudwatch-adapter)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Acloudwatch-adapter&metric=ncloc)](https://sonarcloud.io/dashboard?id=com.exasol%3Acloudwatch-adapter)

This adapter transfers the statistics from the Exasol database to [AWS CloudWatch](https://aws.amazon.com/de/cloudwatch/) metrics. This adapter runs independently of the Exasol database in an AWS Lambda function.

## Setup

### Create an Exasol User

The CloudWatch adapter accesses your Exasol database via its SQL interface. For that it needs credentials. We recommend creating a dedicated user for that purpose:

```sql
CREATE USER CLOUDWATCH_ADAPTER IDENTIFIED BY "<PASSWORD>";

GRANT CREATE SESSION TO CLOUDWATCH_ADAPTER;
```

Don't forget to use a strong, randomly generated password instead of `<PASSWORD>`.

### Store Credentials in AWS Secrets Manager

Create a new secret in the [AWS Secrets Manager](https://aws.amazon.com/secrets-manager/) with the following values:

* `host`: VPC internal ip address of your Exasol database
* `port`: Exasol JDBC port (default: 8563)
* `username`: Name of an Exasol user account with `CREATE SESSION` privileges
* `password`: Password for the account
* `certificateFingerprint`: Fingerprint of the database's TLS certificate. This is only required if Exasol uses a self-signed certificate.

### VPC Setup

The CloudWatch adapter must run in the same AWS VPC that the Exasol database runs. By that it can access the Exasol database using a internal IP address.

From within the VPC it can however not access the default Endpoints for AWS CloudWatch and SecretsManager.

To add them, go to the [AWS VPC Console](https://console.aws.amazon.com/vpc/) / Endpoints. There create endpoints for the following AWS services:

* `com.amazonaws.<REGION>.monitoring`
* `com.amazonaws.<REGION>.secretsmanager`

Make sure that you select the VPC, Subnet and Security group of your Exasol database.

It is important to enable DNS for the endpoint. If it's not possible, you might have to enable DNS in your VPC.

### Setup CloudWatch Adapter

* Open the [AWS Lambda Console](https://console.aws.amazon.com/lambda/)
* Click "Create Function"
* Select "Browse serverless application repository"
* Search for "ExasolCloudWatchAdapter"
* Fill out the application settings
    * `ExaolDeploymentName`: A name describing the Exasol installation you want to monitor. The adapter adds this name as a [dimension](https://docs.aws.amazon.com/AmazonCloudWatch/latest/monitoring/cloudwatch_concepts.html#Dimension) to the metrics in Cloudwatch. This will help you to distinguish the data if you monitor more than one Exasol deployment.

    * `ExasolConnectionSecretArn`: [ARN](https://docs.aws.amazon.com/general/latest/gr/aws-arns-and-namespaces.html) of Secrets Manager secret you created in a previous step.

    * `Metrics`: A comma-separated list of metrics. If empty, the adapter reports all metrics. [List of supported metrics](doc/supported_metrics.md).
    * `SubnetId`: ID of the VPC subnet of the Exasol database.
    * `SecurityGroup`: ID of the security group of the Exasol database.
* Click on "Deploy"

### Create a Dashboard

Now the adapter should transmit the metrics to CloudWatch. To visualize them you have to create a CloudWatch dashboard. You could start from scratch and build your own dashboard. We, however, recommend you to start with our [example dashboard](https://github.com/exasol/cloudwatch-dashboard-examples/). This comes with lots of preconfigured widgets, designed by the best practices of our monitoring experts.

## Troubleshooting

If the adapter does not work properly, first check its log output. For that go to the AWS Management Console / `Lambda`, select the Lambda function of the adapter, and click on `Monitoring`. There click on `View logs in CloudWatch` and scan the log files for error messages.

## Known Bugs

* In case your Exasol database uses a timezone with time-shift as `DBTIMEZONE`, this adapter will **not report** the hour when the time is shifted back, since Exasol stores the statistics entries in the `DBTIMEZONE` and by that, the log entries are ambiguous in that hour (see [#2](https://github.com/exasol/cloudwatch-adapter/issues/2)).
* Due to a bug in SAM we can not publish the app with a retry count set to 0. This can lead to duplicate reported data if the Lambda fails after reporting some points and EventBridge triggers it again (see [#21](https://github.com/exasol/cloudwatch-adapter/issues/21)).

## Additional Information

The design of this adapter ensures that points are never written twice, which would lead to wrong statistics. It does, however not assure that all points are written. In case of temporary errors with the Exasol database or the CloudWatch API, it can occur that data points are missing.

* [Changelog](doc/changes/changelog.md)
* [Dependencies](dependencies.md)

## Information for Developers

You can also modify this adapter and deploy it directly. To do so, [install the AWS SAM cli](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html), go to the `sam/` directory and run `sam deploy --guided`.

