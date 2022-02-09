<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                                           | License                                                   |
| ---------------------------------------------------- | --------------------------------------------------------- |
| [AWS Java SDK :: Services :: Amazon CloudWatch][0]   | [Apache License, Version 2.0][1]                          |
| [AWS Java SDK :: Services :: AWS Secrets Manager][0] | [Apache License, Version 2.0][1]                          |
| [EXASolution JDBC Driver][4]                         | [EXAClient License][5]                                    |
| [error-reporting-java][6]                            | [MIT][7]                                                  |
| [AWS Lambda Java Core Library][8]                    | [Apache License, Version 2.0][1]                          |
| [AWS Lambda Java Events Library][8]                  | [Apache License, Version 2.0][1]                          |
| [AWS Lambda Java Log4j 2.x Libraries][8]             | [Apache License, Version 2.0][1]                          |
| [Apache Log4j API][14]                               | [Apache License, Version 2.0][15]                         |
| [Apache Log4j Core][16]                              | [Apache License, Version 2.0][15]                         |
| [Apache Log4j SLF4J 1.8+ Binding][18]                | [Apache License, Version 2.0][15]                         |
| [JSR 374 (JSON Processing) API][20]                  | [Dual license consisting of the CDDL v1.1 and GPL v2][21] |
| [JSR 374 (JSON Processing) Default Provider][20]     | [Dual license consisting of the CDDL v1.1 and GPL v2][21] |
| [Test Database Builder for Java][24]                 | [MIT][7]                                                  |
| [JUnit5 System Extensions][26]                       | [Eclipse Public License v2.0][27]                         |

## Test Dependencies

| Dependency                                      | License                           |
| ----------------------------------------------- | --------------------------------- |
| [Hamcrest][28]                                  | [BSD License 3][29]               |
| [JUnit Jupiter (Aggregator)][30]                | [Eclipse Public License v2.0][31] |
| [Test containers for Exasol on Docker][32]      | [MIT][7]                          |
| [Testcontainers :: JUnit Jupiter Extension][34] | [MIT][35]                         |
| [mockito-core][36]                              | [The MIT License][37]             |
| [Testcontainers :: Localstack][34]              | [MIT][35]                         |
| [AWS Java SDK for Amazon S3][0]                 | [Apache License, Version 2.0][1]  |
| [EqualsVerifier | release normal jar][42]       | [Apache License, Version 2.0][15] |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [Maven Surefire Plugin][44]                             | [Apache License, Version 2.0][15]              |
| [JaCoCo :: Maven Plugin][46]                            | [Eclipse Public License 2.0][47]               |
| [Apache Maven Compiler Plugin][48]                      | [Apache License, Version 2.0][15]              |
| [Maven Failsafe Plugin][50]                             | [Apache License, Version 2.0][15]              |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][52] | [ASL2][53]                                     |
| [Versions Maven Plugin][54]                             | [Apache License, Version 2.0][15]              |
| [Apache Maven Enforcer Plugin][56]                      | [Apache License, Version 2.0][15]              |
| [Artifact reference checker and unifier][58]            | [MIT][7]                                       |
| [Project keeper maven plugin][60]                       | [MIT][7]                                       |
| [Apache Maven Assembly Plugin][62]                      | [Apache License, Version 2.0][15]              |
| [Apache Maven Shade Plugin][64]                         | [Apache License, Version 2.0][15]              |
| [error-code-crawler-maven-plugin][66]                   | [MIT][7]                                       |
| [OpenFastTrace Maven Plugin][68]                        | [GNU General Public License v3.0][69]          |
| [Reproducible Build Maven Plugin][70]                   | [Apache 2.0][53]                               |
| [Apache Maven JAR Plugin][72]                           | [Apache License, Version 2.0][15]              |
| [Maven Clean Plugin][74]                                | [The Apache Software License, Version 2.0][53] |
| [Maven Resources Plugin][76]                            | [The Apache Software License, Version 2.0][53] |
| [Maven Install Plugin][78]                              | [The Apache Software License, Version 2.0][53] |
| [Maven Deploy Plugin][80]                               | [The Apache Software License, Version 2.0][53] |
| [Maven Site Plugin 3][82]                               | [The Apache Software License, Version 2.0][53] |

[60]: https://github.com/exasol/project-keeper-maven-plugin
[6]: https://github.com/exasol/error-reporting-java
[27]: http://www.eclipse.org/legal/epl-v20.html
[20]: https://javaee.github.io/jsonp
[53]: http://www.apache.org/licenses/LICENSE-2.0.txt
[44]: https://maven.apache.org/surefire/maven-surefire-plugin/
[5]: https://www.exasol.com/support/secure/attachment/155343/EXASOL_SDK-7.0.11.tar.gz
[74]: http://maven.apache.org/plugins/maven-clean-plugin/
[0]: https://aws.amazon.com/sdkforjava
[7]: https://opensource.org/licenses/MIT
[36]: https://github.com/mockito/mockito
[54]: http://www.mojohaus.org/versions-maven-plugin/
[64]: https://maven.apache.org/plugins/maven-shade-plugin/
[29]: http://opensource.org/licenses/BSD-3-Clause
[48]: https://maven.apache.org/plugins/maven-compiler-plugin/
[21]: https://oss.oracle.com/licenses/CDDL+GPL-1.1
[68]: https://github.com/itsallcode/openfasttrace-maven-plugin
[47]: https://www.eclipse.org/legal/epl-2.0/
[18]: https://logging.apache.org/log4j/2.x/log4j-slf4j18-impl/
[46]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[1]: https://aws.amazon.com/apache2.0
[37]: https://github.com/mockito/mockito/blob/main/LICENSE
[70]: http://zlika.github.io/reproducible-build-maven-plugin
[16]: https://logging.apache.org/log4j/2.x/log4j-core/
[30]: https://junit.org/junit5/
[28]: http://hamcrest.org/JavaHamcrest/
[76]: http://maven.apache.org/plugins/maven-resources-plugin/
[58]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[72]: https://maven.apache.org/plugins/maven-jar-plugin/
[14]: https://logging.apache.org/log4j/2.x/log4j-api/
[50]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[24]: https://github.com/exasol/test-db-builder-java
[35]: http://opensource.org/licenses/MIT
[32]: https://github.com/exasol/exasol-testcontainers
[69]: https://www.gnu.org/licenses/gpl-3.0.html
[15]: https://www.apache.org/licenses/LICENSE-2.0.txt
[42]: https://www.jqno.nl/equalsverifier
[56]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[4]: http://www.exasol.com
[31]: https://www.eclipse.org/legal/epl-v20.html
[78]: http://maven.apache.org/plugins/maven-install-plugin/
[52]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[8]: https://aws.amazon.com/lambda/
[34]: https://testcontainers.org
[26]: https://github.com/itsallcode/junit5-system-extensions
[80]: http://maven.apache.org/plugins/maven-deploy-plugin/
[82]: http://maven.apache.org/plugins/maven-site-plugin/
[66]: https://github.com/exasol/error-code-crawler-maven-plugin
[62]: https://maven.apache.org/plugins/maven-assembly-plugin/
