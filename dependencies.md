<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                                           | License                                                                                                        |
| ---------------------------------------------------- | -------------------------------------------------------------------------------------------------------------- |
| [AWS Java SDK :: Services :: Amazon CloudWatch][0]   | [Apache License, Version 2.0][1]                                                                               |
| [AWS Java SDK :: Services :: AWS Secrets Manager][0] | [Apache License, Version 2.0][1]                                                                               |
| [Exasol JDBC Driver][2]                              | [EXAClient License][3]                                                                                         |
| [error-reporting-java][4]                            | [MIT License][5]                                                                                               |
| [AWS Lambda Java Core Library][6]                    | [Apache License, Version 2.0][1]                                                                               |
| [AWS Lambda Java Events Library][6]                  | [Apache License, Version 2.0][1]                                                                               |
| [AWS Lambda Java Log4j 2.x Libraries][6]             | [Apache License, Version 2.0][1]                                                                               |
| [Apache Log4j API][7]                                | [Apache-2.0][8]                                                                                                |
| [Apache Log4j Core][9]                               | [Apache-2.0][8]                                                                                                |
| [SLF4J 1 Binding for Log4j API][10]                  | [Apache-2.0][8]                                                                                                |
| [Jakarta JSON Processing API][11]                    | [Eclipse Public License 2.0][12]; [GNU General Public License, version 2 with the GNU Classpath Exception][13] |
| [Test Database Builder for Java][14]                 | [MIT License][15]                                                                                              |
| [JUnit5 System Extensions][16]                       | [Eclipse Public License v2.0][17]                                                                              |

## Test Dependencies

| Dependency                                      | License                           |
| ----------------------------------------------- | --------------------------------- |
| [Hamcrest][18]                                  | [BSD-3-Clause][19]                |
| [JUnit Jupiter (Aggregator)][20]                | [Eclipse Public License v2.0][21] |
| [Test containers for Exasol on Docker][22]      | [MIT License][23]                 |
| [Testcontainers :: JUnit Jupiter Extension][24] | [MIT][25]                         |
| [mockito-core][26]                              | [MIT][27]                         |
| [Testcontainers :: Localstack][24]              | [MIT][25]                         |
| [AWS Java SDK for Amazon S3][0]                 | [Apache License, Version 2.0][1]  |
| [EqualsVerifier \| release normal jar][28]      | [Apache License, Version 2.0][8]  |

## Runtime Dependencies

| Dependency            | License                                                                                                        |
| --------------------- | -------------------------------------------------------------------------------------------------------------- |
| [Eclipse Parsson][29] | [Eclipse Public License 2.0][12]; [GNU General Public License, version 2 with the GNU Classpath Exception][13] |

## Plugin Dependencies

| Dependency                                              | License                               |
| ------------------------------------------------------- | ------------------------------------- |
| [Apache Maven Clean Plugin][30]                         | [Apache-2.0][8]                       |
| [Apache Maven Install Plugin][31]                       | [Apache-2.0][8]                       |
| [Apache Maven Resources Plugin][32]                     | [Apache-2.0][8]                       |
| [Apache Maven Site Plugin][33]                          | [Apache License, Version 2.0][8]      |
| [SonarQube Scanner for Maven][34]                       | [GNU LGPL 3][35]                      |
| [Apache Maven Toolchains Plugin][36]                    | [Apache-2.0][8]                       |
| [Apache Maven Compiler Plugin][37]                      | [Apache-2.0][8]                       |
| [Apache Maven Enforcer Plugin][38]                      | [Apache-2.0][8]                       |
| [Maven Flatten Plugin][39]                              | [Apache Software Licenese][8]         |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][40] | [ASL2][41]                            |
| [Maven Surefire Plugin][42]                             | [Apache-2.0][8]                       |
| [Versions Maven Plugin][43]                             | [Apache License, Version 2.0][8]      |
| [duplicate-finder-maven-plugin Maven Mojo][44]          | [Apache License 2.0][45]              |
| [Maven Failsafe Plugin][46]                             | [Apache-2.0][8]                       |
| [JaCoCo :: Maven Plugin][47]                            | [EPL-2.0][48]                         |
| [Quality Summarizer Maven Plugin][49]                   | [MIT License][50]                     |
| [error-code-crawler-maven-plugin][51]                   | [MIT License][52]                     |
| [Reproducible Build Maven Plugin][53]                   | [Apache 2.0][41]                      |
| [Exec Maven Plugin][54]                                 | [Apache License 2][8]                 |
| [Project Keeper Maven plugin][55]                       | [The MIT License][56]                 |
| [Apache Maven Shade Plugin][57]                         | [Apache License, Version 2.0][8]      |
| [OpenFastTrace Maven Plugin][58]                        | [GNU General Public License v3.0][59] |

[0]: https://aws.amazon.com/sdkforjava
[1]: https://aws.amazon.com/apache2.0
[2]: http://www.exasol.com/
[3]: https://repo1.maven.org/maven2/com/exasol/exasol-jdbc/24.2.0/exasol-jdbc-24.2.0-license.txt
[4]: https://github.com/exasol/error-reporting-java/
[5]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[6]: https://aws.amazon.com/lambda/
[7]: https://logging.apache.org/log4j/2.x/log4j/log4j-api/
[8]: https://www.apache.org/licenses/LICENSE-2.0.txt
[9]: https://logging.apache.org/log4j/2.x/log4j/log4j-core/
[10]: https://logging.apache.org/log4j/2.x/log4j/log4j-slf4j-impl/
[11]: https://github.com/eclipse-ee4j/jsonp
[12]: https://projects.eclipse.org/license/epl-2.0
[13]: https://projects.eclipse.org/license/secondary-gpl-2.0-cp
[14]: https://github.com/exasol/test-db-builder-java/
[15]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[16]: https://github.com/itsallcode/junit5-system-extensions
[17]: http://www.eclipse.org/legal/epl-v20.html
[18]: http://hamcrest.org/JavaHamcrest/
[19]: https://raw.githubusercontent.com/hamcrest/JavaHamcrest/master/LICENSE
[20]: https://junit.org/junit5/
[21]: https://www.eclipse.org/legal/epl-v20.html
[22]: https://github.com/exasol/exasol-testcontainers/
[23]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[24]: https://java.testcontainers.org
[25]: http://opensource.org/licenses/MIT
[26]: https://github.com/mockito/mockito
[27]: https://opensource.org/licenses/MIT
[28]: https://www.jqno.nl/equalsverifier
[29]: https://github.com/eclipse-ee4j/parsson
[30]: https://maven.apache.org/plugins/maven-clean-plugin/
[31]: https://maven.apache.org/plugins/maven-install-plugin/
[32]: https://maven.apache.org/plugins/maven-resources-plugin/
[33]: https://maven.apache.org/plugins/maven-site-plugin/
[34]: http://sonarsource.github.io/sonar-scanner-maven/
[35]: http://www.gnu.org/licenses/lgpl.txt
[36]: https://maven.apache.org/plugins/maven-toolchains-plugin/
[37]: https://maven.apache.org/plugins/maven-compiler-plugin/
[38]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[39]: https://www.mojohaus.org/flatten-maven-plugin/
[40]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[41]: http://www.apache.org/licenses/LICENSE-2.0.txt
[42]: https://maven.apache.org/surefire/maven-surefire-plugin/
[43]: https://www.mojohaus.org/versions/versions-maven-plugin/
[44]: https://basepom.github.io/duplicate-finder-maven-plugin
[45]: http://www.apache.org/licenses/LICENSE-2.0.html
[46]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[47]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[48]: https://www.eclipse.org/legal/epl-2.0/
[49]: https://github.com/exasol/quality-summarizer-maven-plugin/
[50]: https://github.com/exasol/quality-summarizer-maven-plugin/blob/main/LICENSE
[51]: https://github.com/exasol/error-code-crawler-maven-plugin/
[52]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[53]: http://zlika.github.io/reproducible-build-maven-plugin
[54]: https://www.mojohaus.org/exec-maven-plugin
[55]: https://github.com/exasol/project-keeper/
[56]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[57]: https://maven.apache.org/plugins/maven-shade-plugin/
[58]: https://github.com/itsallcode/openfasttrace-maven-plugin
[59]: https://www.gnu.org/licenses/gpl-3.0.html
