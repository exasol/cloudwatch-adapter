<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                                           | License                                                   |
| ---------------------------------------------------- | --------------------------------------------------------- |
| [AWS Java SDK :: Services :: Amazon CloudWatch][0]   | [Apache License, Version 2.0][1]                          |
| [AWS Java SDK :: Services :: AWS Secrets Manager][0] | [Apache License, Version 2.0][1]                          |
| [EXASolution JDBC Driver][2]                         | [EXAClient License][3]                                    |
| [error-reporting-java][4]                            | [MIT License][5]                                          |
| [AWS Lambda Java Core Library][6]                    | [Apache License, Version 2.0][1]                          |
| [AWS Lambda Java Events Library][6]                  | [Apache License, Version 2.0][1]                          |
| [AWS Lambda Java Log4j 2.x Libraries][6]             | [Apache License, Version 2.0][1]                          |
| [Apache Log4j API][7]                                | [Apache License, Version 2.0][8]                          |
| [Apache Log4j Core][9]                               | [Apache License, Version 2.0][8]                          |
| [Apache Log4j SLF4J Binding][10]                     | [Apache License, Version 2.0][8]                          |
| [JSR 374 (JSON Processing) API][11]                  | [Dual license consisting of the CDDL v1.1 and GPL v2][12] |
| [JSR 374 (JSON Processing) Default Provider][11]     | [Dual license consisting of the CDDL v1.1 and GPL v2][12] |
| [Test Database Builder for Java][13]                 | [MIT License][14]                                         |
| [JUnit5 System Extensions][15]                       | [Eclipse Public License v2.0][16]                         |
| [jackson-databind][17]                               | [The Apache Software License, Version 2.0][8]             |

## Test Dependencies

| Dependency                                      | License                           |
| ----------------------------------------------- | --------------------------------- |
| [Hamcrest][18]                                  | [BSD License 3][19]               |
| [JUnit Jupiter (Aggregator)][20]                | [Eclipse Public License v2.0][21] |
| [Test containers for Exasol on Docker][22]      | [MIT License][23]                 |
| [Testcontainers :: JUnit Jupiter Extension][24] | [MIT][25]                         |
| [mockito-core][26]                              | [The MIT License][27]             |
| [Testcontainers :: Localstack][24]              | [MIT][25]                         |
| [AWS Java SDK for Amazon S3][0]                 | [Apache License, Version 2.0][1]  |
| [EqualsVerifier | release normal jar][28]       | [Apache License, Version 2.0][8]  |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][29]                       | [GNU LGPL 3][30]                               |
| [Apache Maven Compiler Plugin][31]                      | [Apache License, Version 2.0][8]               |
| [Apache Maven Enforcer Plugin][32]                      | [Apache License, Version 2.0][8]               |
| [Maven Flatten Plugin][33]                              | [Apache Software Licenese][34]                 |
| [Exec Maven Plugin][35]                                 | [Apache License 2][34]                         |
| [Project keeper maven plugin][36]                       | [The MIT License][37]                          |
| [Apache Maven Shade Plugin][38]                         | [Apache License, Version 2.0][8]               |
| [OpenFastTrace Maven Plugin][39]                        | [GNU General Public License v3.0][40]          |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][41] | [ASL2][34]                                     |
| [Maven Surefire Plugin][42]                             | [Apache License, Version 2.0][8]               |
| [Versions Maven Plugin][43]                             | [Apache License, Version 2.0][8]               |
| [Maven Failsafe Plugin][44]                             | [Apache License, Version 2.0][8]               |
| [JaCoCo :: Maven Plugin][45]                            | [Eclipse Public License 2.0][46]               |
| [error-code-crawler-maven-plugin][47]                   | [MIT License][48]                              |
| [Reproducible Build Maven Plugin][49]                   | [Apache 2.0][34]                               |
| [Maven Clean Plugin][50]                                | [The Apache Software License, Version 2.0][34] |
| [Maven Resources Plugin][51]                            | [The Apache Software License, Version 2.0][34] |
| [Maven JAR Plugin][52]                                  | [The Apache Software License, Version 2.0][34] |
| [Maven Install Plugin][53]                              | [The Apache Software License, Version 2.0][34] |
| [Maven Deploy Plugin][54]                               | [The Apache Software License, Version 2.0][34] |
| [Maven Site Plugin 3][55]                               | [The Apache Software License, Version 2.0][34] |

[0]: https://aws.amazon.com/sdkforjava
[1]: https://aws.amazon.com/apache2.0
[2]: http://www.exasol.com
[3]: https://www.exasol.com/support/secure/attachment/155343/EXASOL_SDK-7.0.11.tar.gz
[4]: https://github.com/exasol/error-reporting-java/
[5]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[6]: https://aws.amazon.com/lambda/
[7]: https://logging.apache.org/log4j/2.x/log4j-api/
[8]: https://www.apache.org/licenses/LICENSE-2.0.txt
[9]: https://logging.apache.org/log4j/2.x/log4j-core/
[10]: https://logging.apache.org/log4j/2.x/log4j-slf4j-impl/
[11]: https://javaee.github.io/jsonp
[12]: https://oss.oracle.com/licenses/CDDL+GPL-1.1
[13]: https://github.com/exasol/test-db-builder-java/
[14]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[15]: https://github.com/itsallcode/junit5-system-extensions
[16]: http://www.eclipse.org/legal/epl-v20.html
[17]: https://github.com/FasterXML/jackson
[18]: http://hamcrest.org/JavaHamcrest/
[19]: http://opensource.org/licenses/BSD-3-Clause
[20]: https://junit.org/junit5/
[21]: https://www.eclipse.org/legal/epl-v20.html
[22]: https://github.com/exasol/exasol-testcontainers/
[23]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[24]: https://testcontainers.org
[25]: http://opensource.org/licenses/MIT
[26]: https://github.com/mockito/mockito
[27]: https://github.com/mockito/mockito/blob/main/LICENSE
[28]: https://www.jqno.nl/equalsverifier
[29]: http://sonarsource.github.io/sonar-scanner-maven/
[30]: http://www.gnu.org/licenses/lgpl.txt
[31]: https://maven.apache.org/plugins/maven-compiler-plugin/
[32]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[33]: https://www.mojohaus.org/flatten-maven-plugin/
[34]: http://www.apache.org/licenses/LICENSE-2.0.txt
[35]: http://www.mojohaus.org/exec-maven-plugin
[36]: https://github.com/exasol/project-keeper/
[37]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[38]: https://maven.apache.org/plugins/maven-shade-plugin/
[39]: https://github.com/itsallcode/openfasttrace-maven-plugin
[40]: https://www.gnu.org/licenses/gpl-3.0.html
[41]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[42]: https://maven.apache.org/surefire/maven-surefire-plugin/
[43]: http://www.mojohaus.org/versions-maven-plugin/
[44]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[45]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[46]: https://www.eclipse.org/legal/epl-2.0/
[47]: https://github.com/exasol/error-code-crawler-maven-plugin/
[48]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[49]: http://zlika.github.io/reproducible-build-maven-plugin
[50]: http://maven.apache.org/plugins/maven-clean-plugin/
[51]: http://maven.apache.org/plugins/maven-resources-plugin/
[52]: http://maven.apache.org/plugins/maven-jar-plugin/
[53]: http://maven.apache.org/plugins/maven-install-plugin/
[54]: http://maven.apache.org/plugins/maven-deploy-plugin/
[55]: http://maven.apache.org/plugins/maven-site-plugin/
