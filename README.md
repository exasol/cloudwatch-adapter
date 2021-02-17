# Exasol – AWS Cloudwatch Adapter

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

* `host`: Hostname of the Exasol database to connect to (this can either be a public, or a VPC local ip address)
* `port`: Exasol JDBC port (default: 8563)
* `username`: Name of an Exasol user account with `CREATE SESSION` privileges
* `password`: Password for the account

### Setup CloudWatch Adapter

* Open the [AWS Lambda Console](https://console.aws.amazon.com/lambda/)
* Click "Create Function"
* Select "Browse serverless application repository"
* Search for "ExasolCloudWatchAdapter"
* Fill out the application settings (see [configuration section](#configuration))
* Click on "Deploy"

#### VPC configuration

You should run CloudWatch adapter in the same AWS VPC that runs Exasol database. By that it can access the Exasol database using a local IP address.
If your database does not run on AWS you can skip this step an access your Exasol using its public IP address.

To do so, first finish the configuration with the local IP of the Exasol database. Next open the settings for the deployed Lambda function, go to its settings / VPC / Edit. There you can add the VPC configuration of your Exasol database.

### Create a Dashboard

Now the adapter should transmit the metrics to CloudWatch. To visualize them you have to create a CloudWatch dashboard. You could start from scratch and build your own dashboard. We, however, recommend you to start with our [example dashboard](https://github.com/exasol/cloudwatch-dashboard-examples/). This comes with lots of preconfigured widgets, designed by the best practices of our monitoring experts.

## Configuration

You can configure the adapter using the following properties:

* `ExaolDeploymentName`: A name describing the Exasol installation you want to monitor. The adapter adds this name as a [dimension](https://docs.aws.amazon.com/AmazonCloudWatch/latest/monitoring/cloudwatch_concepts.html#Dimension) to the metrics in Cloudwatch. This will help you to distinguish the data if you monitor more than one Exasol deployment.

* `ExasolConnectionSecretArn`: [ARN](https://docs.aws.amazon.com/general/latest/gr/aws-arns-and-namespaces.html) of Secrets Manager secret you created in a previous step.

* `Metrics`: A comma-separated list of metrics. If empty, the adapter reports all metrics. [List of supported metrics](doc/supported_metrics.md).

## Troubleshooting

If the adapter does not work properly, first check its log output. For that go to the AWS Management Console / `Lambda`, select the Lambda function of the adapter, and click on `Monitoring`. There click on `View logs in CloudWatch` and scan the log files for error messages.

## Known Bugs

* In case your Exasol database uses a timezone with time-shift as `DBTIMEZONE`, this adapter will **not report** the hour when the time is shifted back, since Exasol stores the statistics entries in the `DBTIMEZONE` and by that, the log entries are ambiguous in that hour (see [#2](https://github.com/exasol/cloudwatch-adapter/issues/2)).

## Information for Users

The design of this adapter ensures that points are never written twice, which would lead to wrong statistics. It does, however not assure that all points are written. In case of temporary errors with the Exasol database or the CloudWatch API, it can occur that data points are missing.

* [Changelog](doc/changes/changelog.md)
* [Dependencies](doc/dependencies.md)

## Information for Developers

You can also modify this adapter and deploy it directly. To do so, [install the AWS SAM cli](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html), go to the `sam/` directory and run `sam deploy --guided`.


