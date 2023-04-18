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

## Test Dependencies

| Dependency                                      | License                           |
| ----------------------------------------------- | --------------------------------- |
| [Hamcrest][17]                                  | [BSD License 3][18]               |
| [JUnit Jupiter (Aggregator)][19]                | [Eclipse Public License v2.0][20] |
| [Test containers for Exasol on Docker][21]      | [MIT License][22]                 |
| [Testcontainers :: JUnit Jupiter Extension][23] | [MIT][24]                         |
| [mockito-core][25]                              | [The MIT License][26]             |
| [Testcontainers :: Localstack][23]              | [MIT][24]                         |
| [AWS Java SDK for Amazon S3][0]                 | [Apache License, Version 2.0][1]  |
| [EqualsVerifier | release normal jar][27]       | [Apache License, Version 2.0][8]  |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][28]                       | [GNU LGPL 3][29]                               |
| [Apache Maven Compiler Plugin][30]                      | [Apache-2.0][8]                                |
| [Apache Maven Enforcer Plugin][31]                      | [Apache-2.0][8]                                |
| [Maven Flatten Plugin][32]                              | [Apache Software Licenese][8]                  |
| [Exec Maven Plugin][33]                                 | [Apache License 2][8]                          |
| [Project keeper maven plugin][34]                       | [The MIT License][35]                          |
| [Apache Maven Shade Plugin][36]                         | [Apache License, Version 2.0][8]               |
| [OpenFastTrace Maven Plugin][37]                        | [GNU General Public License v3.0][38]          |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][39] | [ASL2][40]                                     |
| [Maven Surefire Plugin][41]                             | [Apache-2.0][8]                                |
| [Versions Maven Plugin][42]                             | [Apache License, Version 2.0][8]               |
| [duplicate-finder-maven-plugin Maven Mojo][43]          | [Apache License 2.0][44]                       |
| [Maven Failsafe Plugin][45]                             | [Apache-2.0][8]                                |
| [JaCoCo :: Maven Plugin][46]                            | [Eclipse Public License 2.0][47]               |
| [error-code-crawler-maven-plugin][48]                   | [MIT License][49]                              |
| [Reproducible Build Maven Plugin][50]                   | [Apache 2.0][40]                               |
| [Maven Clean Plugin][51]                                | [The Apache Software License, Version 2.0][40] |
| [Maven Resources Plugin][52]                            | [The Apache Software License, Version 2.0][40] |
| [Maven JAR Plugin][53]                                  | [The Apache Software License, Version 2.0][40] |
| [Maven Install Plugin][54]                              | [The Apache Software License, Version 2.0][40] |
| [Maven Deploy Plugin][55]                               | [The Apache Software License, Version 2.0][40] |
| [Maven Site Plugin 3][56]                               | [The Apache Software License, Version 2.0][40] |

[0]: https://aws.amazon.com/sdkforjava
[1]: https://aws.amazon.com/apache2.0
[2]: http://www.exasol.com
[3]: https://repo1.maven.org/maven2/com/exasol/exasol-jdbc/7.1.19/exasol-jdbc-7.1.19-license.txt
[4]: https://github.com/exasol/error-reporting-java/
[5]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[6]: https://aws.amazon.com/lambda/
[7]: https://logging.apache.org/log4j/2.x/log4j-api/
[8]: https://www.apache.org/licenses/LICENSE-2.0.txt
[9]: https://logging.apache.org/log4j/2.x/log4j-core/
[10]: https://logging.apache.org/log4j/2.x/log4j-slf4j-impl.html
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
[21]: https://github.com/exasol/exasol-testcontainers/
[22]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[23]: https://testcontainers.org
[24]: http://opensource.org/licenses/MIT
[25]: https://github.com/mockito/mockito
[26]: https://github.com/mockito/mockito/blob/main/LICENSE
[27]: https://www.jqno.nl/equalsverifier
[28]: http://sonarsource.github.io/sonar-scanner-maven/
[29]: http://www.gnu.org/licenses/lgpl.txt
[30]: https://maven.apache.org/plugins/maven-compiler-plugin/
[31]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[32]: https://www.mojohaus.org/flatten-maven-plugin/
[33]: https://www.mojohaus.org/exec-maven-plugin
[34]: https://github.com/exasol/project-keeper/
[35]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[36]: https://maven.apache.org/plugins/maven-shade-plugin/
[37]: https://github.com/itsallcode/openfasttrace-maven-plugin
[38]: https://www.gnu.org/licenses/gpl-3.0.html
[39]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[40]: http://www.apache.org/licenses/LICENSE-2.0.txt
[41]: https://maven.apache.org/surefire/maven-surefire-plugin/
[42]: https://www.mojohaus.org/versions/versions-maven-plugin/
[43]: https://github.com/basepom/duplicate-finder-maven-plugin
[44]: http://www.apache.org/licenses/LICENSE-2.0.html
[45]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[46]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[47]: https://www.eclipse.org/legal/epl-2.0/
[48]: https://github.com/exasol/error-code-crawler-maven-plugin/
[49]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[50]: http://zlika.github.io/reproducible-build-maven-plugin
[51]: http://maven.apache.org/plugins/maven-clean-plugin/
[52]: http://maven.apache.org/plugins/maven-resources-plugin/
[53]: http://maven.apache.org/plugins/maven-jar-plugin/
[54]: http://maven.apache.org/plugins/maven-install-plugin/
[55]: http://maven.apache.org/plugins/maven-deploy-plugin/
[56]: http://maven.apache.org/plugins/maven-site-plugin/
