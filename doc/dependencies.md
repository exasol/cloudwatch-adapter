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