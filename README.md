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

If your Exasol database runs on AWS and in a VPC you should run this adapter in the same VPC. By that it can access the Exasol database using a local IP address.

To do so, first finish the configuration with the local IP of the exasol database. Next open the settings for the deployed Lambda function, go to its settings / VPC / Edit. There you can add the VPC configuration of your Exasol database.

### Create a Dashboard

Now the adapter should transmit the metrics to CloudWatch. To visualize them you have to create a CloudWatch dashboard. You could now start from scratch and build your own dashboard. We, however, recommend you to start with our [example dashboard](https://github.com/exasol/cloudwatch-dashboard-examples/). This comes with lots of preconfigured widgets, designed by the best practices of our monitoring experts.

## Configuration

You can configure the adapter using the following properties:

* `ExaolDeploymentName`: A name describing the Exasol installation you want to monitor. The adapter adds this name as a [dimension](https://docs.aws.amazon.com/AmazonCloudWatch/latest/monitoring/cloudwatch_concepts.html#Dimension) to the metrics in Cloudwatch. This will help you to distinguish the data if you monitor more than one Exasol deployment.

* `ExasolConnectionSecretArn`: [ARN](https://docs.aws.amazon.com/general/latest/gr/aws-arns-and-namespaces.html) of Secrets Manager secret you created in a previous step.


* `Metrics`: A comma-separated list of metrics. If empty, the adapter reports all metrics. Available metrics:
  <!-- DON'T CHANGE THIS BY HAND! Use ReadmeGenerator#printMetrics() instead! -->
  [`RAW_OBJECT_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_DB_SIZE_LAST_DAY), [`MEM_OBJECT_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_DB_SIZE_LAST_DAY), [`AUXILIARY_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_DB_SIZE_LAST_DAY), [`STATISTICS_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_DB_SIZE_LAST_DAY), [`RECOMMENDED_DB_RAM_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_DB_SIZE_LAST_DAY), [`STORAGE_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_DB_SIZE_LAST_DAY), [`USE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_DB_SIZE_LAST_DAY), [`TEMP_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_DB_SIZE_LAST_DAY)
  , [`OBJECT_COUNT`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_DB_SIZE_LAST_DAY), [`LOAD`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`CPU`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`TEMP_DB_RAM`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`PERSISTENT_DB_RAM`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`HDD_READ`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`HDD_WRITE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`LOCAL_READ_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY)
  , [`LOCAL_READ_DURATION`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`LOCAL_WRITE_DURATION`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`CACHE_READ_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`CACHE_READ_DURATION`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`CACHE_WRITE_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`CACHE_WRITE_DURATION`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`REMOTE_READ_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`REMOTE_READ_DURATION`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY)
  , [`REMOTE_WRITE_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`REMOTE_WRITE_DURATION`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`NET`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`SWAP`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_MONITOR_LAST_DAY), [`USERS`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_USAGE_LAST_DAY), [`QUERIES`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_USAGE_LAST_DAY), [`NODES`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_SYSTEM_EVENTS), [`DB_RAM_SIZE`](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm#EXA_SYSTEM_EVENTS)

  For a reference check the [Exasol documentation](https://docs.exasol.com/sql_references/metadata/statistical_system_table.htm).

## Troubleshooting

If the adapter does not work properly, first check its log output. For that go to the AWS Management Console / `Lambda`, select the Lambda function of the adapter, and click on `Monitoring`. There click on `View logs in CloudWatch` and scan the log files for error messages.

## Information for Users

The design of this adapter ensures that points are never written twice, which would lead to wrong statistics. It does, however not assure that all points are written. In case of temporary errors with the Exasol database or the CloudWatch API, it can occur that data points are missing.

* [Changelog](doc/changes/changelog.md)

## Information for Developers

You can also modify this adapter and deploy it directly. To do so, [install the AWS SAM cli](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html), go to the `sam/` directory and run `sam deploy --guided`.

### Known Bugs

* In case your Exasol database uses a timezone with time-shift as `DBTIMEZONE`, this adapter will **not report** the hour when the time is shifted back, since Exasol stores the statistics entries in the `DBTIMEZONE` and by that, the log entries are ambiguous in that hour (see [#2](https://github.com/exasol/cloudwatch-adapter/issues/2)).

## Dependencies

### Run Time Dependencies

Running the project requires a Java Runtime version 11 or later.

| Dependency                                                         | Purpose                                                | License                       |
|--------------------------------------------------------------------|--------------------------------------------------------|-------------------------------|
| [AWS SDK Cloudwatch][aws-java-sdk-cloudwatch]                      | AWS Java SDK Cloudwatch.                               | Apache License 2.0            |
| [AWS SDK Secretsmanager][aws-java-sdk-secrets]                     | Interface for accessing AWS Secrets Manager.           | Apache License 2.0            |
| [AWS Lambda Core][aws-java-lambda-core]                            | Provides Lambda Context interfaces that Lambda accepts.| Apache License 2.0            |
| [AWS Lambda Events][aws-java-lambda-events]                        | Defines event sources that AWS Lambda natively accepts.| Apache License 2.0            |
| [AWS Lambda Log4J][aws-java-lambda-log4j]                          | Defines the Lambda adapter to use with log4j version 2.| Apache License 2.0            |
| [Log4J Core][log4j-core]                                           | Apache Log4j implementation.                           | Apache License 2.0            |
| [Log4J Api][log4j-api]                                             | Provides Log4J Api interface to applications.          | Apache License 2.0            |
| [Log4J SLF4J Impl][log4j-slf4j-impl]                               | Provides the Log4j 2 SLF4J Binding.                    | Apache License 2.0            |
| [Javax JSON Api][javax-json-api]                                   | Java API for JSON Processing.                          | CDDL v1.1 and GPL v2          |
| [Glassfish JSON][glassfish-json]                                   | Java API for JSON Processing.                          | CDDL v1.1 and GPL v2          |
| [Exasol JDBC][exasol-jdbc-driver]                                  | JDBC Driver for Exasol database.                       | MIT License                   |
| [Exasol Error Reporting][error-reporting-java]                     | Java builders for Exasol error messages.               | MIT License                   |

### Test Dependencies

| Dependency                                                         | Purpose                                                | License                       |
|--------------------------------------------------------------------|--------------------------------------------------------|-------------------------------|
| [Apache Maven](https://maven.apache.org/)                          | Build tool                                             | Apache License 2.0            | 
| [Java Hamcrest](http://hamcrest.org/JavaHamcrest/)                 | Checking for conditions in code via matchers           | BSD License                   |
| [JUnit](https://junit.org/junit5)                                  | Unit testing framework                                 | Eclipse Public License 1.0    |
| [Mockito](http://site.mockito.org/)                                | Mocking framework                                      | MIT License                   |
| [AWS SDK S3][aws-java-sdk-s3]                                      | Interface for accessing AWS S3.                        | Apache License 2.0            |
| [Equals Verifier](https://jqno.nl/equalsverifier/)                 | Testing `equals(...)` and `hashCode()` contracts       | Apache License 2.0            |
| [System Lambda](https://github.com/stefanbirkner/system-lambda)    | Functions for testing code that use `java.lang.System` | MIT License                   |
| [Junit5 System Extensions][junit5-system-extensions]               | JUnit5 extensions to test Java System.x functions      | EPL-2.0 License               |
| [Exasol Testcontainers][exasol-testcontainers]                     | Exasol extension for the Testcontainers framework      | MIT License                   |
| [Hamcrest ResultSet Matcher][hamcrest-resultset-matcher]           | Hamcrest extension for matching ResultSets             | MIT License                   |
| [Exasol Test Database Builder][exasol-tddb]                        | Library to create and clean up database in tests       | MIT License                   |
| [Localstack Testcontainers][localstack-testcontainers]             | A local docker AWS cloud containers                    | MIT License                   |

### Maven Plug-ins

| Plug-in                                                            | Purpose                                                | License                       |
|--------------------------------------------------------------------|--------------------------------------------------------|-------------------------------|
| [Maven Surefire Plugin][maven-surefire-plugin]                     | Unit testing                                           | Apache License 2.0            |
| [Maven Jacoco Plugin][maven-jacoco-plugin]                         | Code coverage metering                                 | Eclipse Public License 2.0    |
| [Maven Compiler Plugin][maven-compiler-plugin]                     | Setting required Java version                          | Apache License 2.0            |
| [Maven Failsafe Plugin][maven-failsafe-plugin]                     | Integration testing                                    | Apache License 2.0            |
| [Sonatype OSS Index Maven Plugin][sonatype-oss-index-maven-plugin] | Checking Dependencies Vulnerability                    | ASL2                          |
| [Versions Maven Plugin][versions-maven-plugin]                     | Checking if dependencies updates are available         | Apache License 2.0            |
| [Maven Enforcer Plugin][maven-enforcer-plugin]                     | Controlling environment constants                      | Apache License 2.0            |
| [Project Keeper Maven Plugin][project-keeper-maven-plugin]         | Checking project structure                             | MIT License                   |

[maven-surefire-plugin]: https://maven.apache.org/surefire/maven-surefire-plugin/

[maven-jacoco-plugin]: https://www.eclemma.org/jacoco/trunk/doc/maven.html

[maven-compiler-plugin]: https://maven.apache.org/plugins/maven-compiler-plugin/

[maven-assembly-plugin]: https://maven.apache.org/plugins/maven-assembly-plugin/

[maven-failsafe-plugin]: https://maven.apache.org/surefire/maven-failsafe-plugin/

[sonatype-oss-index-maven-plugin]: https://sonatype.github.io/ossindex-maven/maven-plugin/

[versions-maven-plugin]: https://www.mojohaus.org/versions-maven-plugin/

[maven-enforcer-plugin]: http://maven.apache.org/enforcer/maven-enforcer-plugin/

[artifact-ref-checker-plugin]: https://github.com/exasol/artifact-reference-checker-maven-plugin

[project-keeper-maven-plugin]: https://github.com/exasol/project-keeper-maven-plugin

[exasol-jdbc-driver]: https://www.exasol.com/portal/display/DOWNLOAD/Exasol+Download+Section

[error-reporting-java]: https://gihub.com/exasol/error-reporting-java/

[exasol-testcontainers]: https://github.com/exasol/exasol-testcontainers

[exasol-tddb]: https://github.com/exasol/test-db-builder-java

[hamcrest-resultset-matcher]: https://github.com/exasol/hamcrest-resultset-matcher

[localstack-testcontainers]: https://www.testcontainers.org/modules/localstack

[aws-java-sdk-cloudwatch]: https://github.com/aws/aws-sdk-java/tree/master/aws-java-sdk-cloudwatch

[aws-java-sdk-secrets]: https://github.com/aws/aws-sdk-java/tree/master/aws-java-sdk-secretsmanager

[aws-java-sdk-s3]: https://github.com/aws/aws-sdk-java/tree/master/aws-java-sdk-s3

[aws-java-lambda-code]: https://github.com/aws/aws-lambda-java-libs/tree/master/aws-lambda-java-core

[aws-java-lambda-events]: https://github.com/aws/aws-lambda-java-libs/tree/master/aws-lambda-java-events

[aws-java-lambda-log4j]: https://github.com/aws/aws-lambda-java-libs/tree/master/aws-lambda-java-log4j

[log4j-core]: https://logging.apache.org/log4j/2.x/log4j-core/

[log4j-api]: https://logging.apache.org/log4j/2.x/log4j-api/

[log4j-slf4j-impl]: https://logging.apache.org/log4j/2.x/log4j-slf4j-impl/

[javax-json-api]: https://javaee.github.io/jsonp/

[glassfish-json]: https://javaee.github.io/jsonp/

[junit5-system-extensions]: https://github.com/itsallcode/junit5-system-extensions
