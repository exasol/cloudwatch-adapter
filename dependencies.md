<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                                           | License                                                   |
| ---------------------------------------------------- | --------------------------------------------------------- |
| [AWS Java SDK :: Services :: Amazon CloudWatch][0]   | [Apache License, Version 2.0][1]                          |
| [AWS Java SDK :: Services :: AWS Secrets Manager][0] | [Apache License, Version 2.0][1]                          |
| [EXASolution JDBC Driver][2]                         | [EXAClient License][3]                                    |
| [error-reporting-java][4]                            | [MIT][5]                                                  |
| [AWS Lambda Java Core Library][6]                    | [Apache License, Version 2.0][1]                          |
| [AWS Lambda Java Events Library][6]                  | [Apache License, Version 2.0][1]                          |
| [AWS Lambda Java Log4j 2.x Libraries][6]             | [Apache License, Version 2.0][1]                          |
| [Apache Log4j API][7]                                | [Apache License, Version 2.0][8]                          |
| [Apache Log4j Core][9]                               | [Apache License, Version 2.0][8]                          |
| [Apache Log4j SLF4J 1.8+ Binding][10]                | [Apache License, Version 2.0][8]                          |
| [JSR 374 (JSON Processing) API][11]                  | [Dual license consisting of the CDDL v1.1 and GPL v2][12] |
| [JSR 374 (JSON Processing) Default Provider][11]     | [Dual license consisting of the CDDL v1.1 and GPL v2][12] |
| [Test Database Builder for Java][13]                 | [MIT License][14]                                         |
| [JUnit5 System Extensions][15]                       | [Eclipse Public License v2.0][16]                         |

## Test Dependencies

| Dependency                                      | License                           |
| ----------------------------------------------- | --------------------------------- |
| [Hamcrest][17]                                  | [BSD License 3][18]               |
| [JUnit Jupiter (Aggregator)][19]                | [Eclipse Public License v2.0][20] |
| [Test containers for Exasol on Docker][21]      | [MIT][5]                          |
| [Testcontainers :: JUnit Jupiter Extension][22] | [MIT][23]                         |
| [mockito-core][24]                              | [The MIT License][25]             |
| [Testcontainers :: Localstack][22]              | [MIT][23]                         |
| [AWS Java SDK for Amazon S3][0]                 | [Apache License, Version 2.0][1]  |
| [EqualsVerifier | release normal jar][26]       | [Apache License, Version 2.0][8]  |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][27]                       | [GNU LGPL 3][28]                               |
| [Apache Maven Compiler Plugin][29]                      | [Apache License, Version 2.0][8]               |
| [Apache Maven Enforcer Plugin][30]                      | [Apache License, Version 2.0][8]               |
| [Maven Flatten Plugin][31]                              | [Apache Software Licenese][32]                 |
| [Exec Maven Plugin][33]                                 | [Apache License 2][32]                         |
| [Project keeper maven plugin][34]                       | [The MIT License][35]                          |
| [Apache Maven Shade Plugin][36]                         | [Apache License, Version 2.0][8]               |
| [OpenFastTrace Maven Plugin][37]                        | [GNU General Public License v3.0][38]          |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][39] | [ASL2][32]                                     |
| [Reproducible Build Maven Plugin][40]                   | [Apache 2.0][32]                               |
| [Maven Surefire Plugin][41]                             | [Apache License, Version 2.0][8]               |
| [Versions Maven Plugin][42]                             | [Apache License, Version 2.0][8]               |
| [Maven Failsafe Plugin][43]                             | [Apache License, Version 2.0][8]               |
| [JaCoCo :: Maven Plugin][44]                            | [Eclipse Public License 2.0][45]               |
| [error-code-crawler-maven-plugin][46]                   | [MIT][5]                                       |
| [Maven Clean Plugin][47]                                | [The Apache Software License, Version 2.0][32] |
| [Maven Resources Plugin][48]                            | [The Apache Software License, Version 2.0][32] |
| [Maven JAR Plugin][49]                                  | [The Apache Software License, Version 2.0][32] |
| [Maven Install Plugin][50]                              | [The Apache Software License, Version 2.0][32] |
| [Maven Deploy Plugin][51]                               | [The Apache Software License, Version 2.0][32] |
| [Maven Site Plugin 3][52]                               | [The Apache Software License, Version 2.0][32] |

[0]: https://aws.amazon.com/sdkforjava
[1]: https://aws.amazon.com/apache2.0
[2]: http://www.exasol.com
[3]: https://www.exasol.com/support/secure/attachment/155343/EXASOL_SDK-7.0.11.tar.gz
[4]: https://github.com/exasol/error-reporting-java
[5]: https://opensource.org/licenses/MIT
[6]: https://aws.amazon.com/lambda/
[7]: https://logging.apache.org/log4j/2.x/log4j-api/
[8]: https://www.apache.org/licenses/LICENSE-2.0.txt
[9]: https://logging.apache.org/log4j/2.x/log4j-core/
[10]: https://logging.apache.org/log4j/2.x/log4j-slf4j18-impl/
[11]: https://javaee.github.io/jsonp
[12]: https://oss.oracle.com/licenses/CDDL+GPL-1.1
[13]: https://github.com/exasol/test-db-builder-java/
[14]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[15]: https://github.com/itsallcode/junit5-system-extensions
[16]: http://www.eclipse.org/legal/epl-v20.html
[17]: http://hamcrest.org/JavaHamcrest/
[18]: http://opensource.org/licenses/BSD-3-Clause
[19]: https://junit.org/junit5/
[20]: https://www.eclipse.org/legal/epl-v20.html
[21]: https://github.com/exasol/exasol-testcontainers
[22]: https://testcontainers.org
[23]: http://opensource.org/licenses/MIT
[24]: https://github.com/mockito/mockito
[25]: https://github.com/mockito/mockito/blob/main/LICENSE
[26]: https://www.jqno.nl/equalsverifier
[27]: http://sonarsource.github.io/sonar-scanner-maven/
[28]: http://www.gnu.org/licenses/lgpl.txt
[29]: https://maven.apache.org/plugins/maven-compiler-plugin/
[30]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[31]: https://www.mojohaus.org/flatten-maven-plugin/
[32]: http://www.apache.org/licenses/LICENSE-2.0.txt
[33]: http://www.mojohaus.org/exec-maven-plugin
[34]: https://github.com/exasol/project-keeper/
[35]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[36]: https://maven.apache.org/plugins/maven-shade-plugin/
[37]: https://github.com/itsallcode/openfasttrace-maven-plugin
[38]: https://www.gnu.org/licenses/gpl-3.0.html
[39]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[40]: http://zlika.github.io/reproducible-build-maven-plugin
[41]: https://maven.apache.org/surefire/maven-surefire-plugin/
[42]: http://www.mojohaus.org/versions-maven-plugin/
[43]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[44]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[45]: https://www.eclipse.org/legal/epl-2.0/
[46]: https://github.com/exasol/error-code-crawler-maven-plugin
[47]: http://maven.apache.org/plugins/maven-clean-plugin/
[48]: http://maven.apache.org/plugins/maven-resources-plugin/
[49]: http://maven.apache.org/plugins/maven-jar-plugin/
[50]: http://maven.apache.org/plugins/maven-install-plugin/
[51]: http://maven.apache.org/plugins/maven-deploy-plugin/
[52]: http://maven.apache.org/plugins/maven-site-plugin/
