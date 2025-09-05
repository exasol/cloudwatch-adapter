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
| [Apache Log4j Core][7]                               | [Apache-2.0][8]                                                                                                |
| [SLF4J 1 Binding for Log4j API][7]                   | [Apache-2.0][8]                                                                                                |
| [Jakarta JSON Processing API][9]                     | [Eclipse Public License 2.0][10]; [GNU General Public License, version 2 with the GNU Classpath Exception][11] |
| [Test Database Builder for Java][12]                 | [MIT License][13]                                                                                              |
| [JUnit5 System Extensions][14]                       | [Eclipse Public License v2.0][15]                                                                              |

## Test Dependencies

| Dependency                                      | License                           |
| ----------------------------------------------- | --------------------------------- |
| [Hamcrest][16]                                  | [BSD-3-Clause][17]                |
| [JUnit Jupiter API][18]                         | [Eclipse Public License v2.0][19] |
| [JUnit Jupiter Params][18]                      | [Eclipse Public License v2.0][19] |
| [Test containers for Exasol on Docker][20]      | [MIT License][21]                 |
| [Testcontainers :: JUnit Jupiter Extension][22] | [MIT][23]                         |
| [mockito-core][24]                              | [MIT][25]                         |
| [Testcontainers :: Localstack][22]              | [MIT][23]                         |
| [EqualsVerifier \| release normal jar][26]      | [Apache License, Version 2.0][8]  |

## Runtime Dependencies

| Dependency            | License                                                                                                        |
| --------------------- | -------------------------------------------------------------------------------------------------------------- |
| [Eclipse Parsson][27] | [Eclipse Public License 2.0][10]; [GNU General Public License, version 2 with the GNU Classpath Exception][11] |

## Plugin Dependencies

| Dependency                                              | License                                     |
| ------------------------------------------------------- | ------------------------------------------- |
| [Apache Maven Clean Plugin][28]                         | [Apache-2.0][8]                             |
| [Apache Maven Install Plugin][29]                       | [Apache-2.0][8]                             |
| [Apache Maven Resources Plugin][30]                     | [Apache-2.0][8]                             |
| [Apache Maven Site Plugin][31]                          | [Apache-2.0][8]                             |
| [SonarQube Scanner for Maven][32]                       | [GNU LGPL 3][33]                            |
| [Apache Maven Toolchains Plugin][34]                    | [Apache-2.0][8]                             |
| [Apache Maven Compiler Plugin][35]                      | [Apache-2.0][8]                             |
| [Apache Maven Enforcer Plugin][36]                      | [Apache-2.0][8]                             |
| [Maven Flatten Plugin][37]                              | [Apache Software Licenese][8]               |
| [Exec Maven Plugin][38]                                 | [Apache License 2][8]                       |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][39] | [ASL2][40]                                  |
| [Maven Surefire Plugin][41]                             | [Apache-2.0][8]                             |
| [Versions Maven Plugin][42]                             | [Apache License, Version 2.0][8]            |
| [duplicate-finder-maven-plugin Maven Mojo][43]          | [Apache License 2.0][44]                    |
| [Apache Maven Artifact Plugin][45]                      | [Apache-2.0][8]                             |
| [Maven Failsafe Plugin][46]                             | [Apache-2.0][8]                             |
| [JaCoCo :: Maven Plugin][47]                            | [EPL-2.0][48]                               |
| [Quality Summarizer Maven Plugin][49]                   | [MIT License][50]                           |
| [error-code-crawler-maven-plugin][51]                   | [MIT License][52]                           |
| [Git Commit Id Maven Plugin][53]                        | [GNU Lesser General Public License 3.0][54] |
| [Project Keeper Maven plugin][55]                       | [The MIT License][56]                       |
| [Apache Maven Shade Plugin][57]                         | [Apache-2.0][8]                             |
| [OpenFastTrace Maven Plugin][58]                        | [GNU General Public License v3.0][59]       |

[0]: https://aws.amazon.com/sdkforjava
[1]: https://aws.amazon.com/apache2.0
[2]: https://www.exasol.com/
[3]: https://repo1.maven.org/maven2/com/exasol/exasol-jdbc/25.2.5/exasol-jdbc-25.2.5-license.txt
[4]: https://github.com/exasol/error-reporting-java/
[5]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[6]: https://aws.amazon.com/lambda/
[7]: https://logging.apache.org/log4j/2.x/
[8]: https://www.apache.org/licenses/LICENSE-2.0.txt
[9]: https://github.com/eclipse-ee4j/jsonp
[10]: https://projects.eclipse.org/license/epl-2.0
[11]: https://projects.eclipse.org/license/secondary-gpl-2.0-cp
[12]: https://github.com/exasol/test-db-builder-java/
[13]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[14]: https://github.com/itsallcode/junit5-system-extensions
[15]: http://www.eclipse.org/legal/epl-v20.html
[16]: http://hamcrest.org/JavaHamcrest/
[17]: https://raw.githubusercontent.com/hamcrest/JavaHamcrest/master/LICENSE
[18]: https://junit.org/
[19]: https://www.eclipse.org/legal/epl-v20.html
[20]: https://github.com/exasol/exasol-testcontainers/
[21]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[22]: https://java.testcontainers.org
[23]: http://opensource.org/licenses/MIT
[24]: https://github.com/mockito/mockito
[25]: https://opensource.org/licenses/MIT
[26]: https://www.jqno.nl/equalsverifier
[27]: https://github.com/eclipse-ee4j/parsson
[28]: https://maven.apache.org/plugins/maven-clean-plugin/
[29]: https://maven.apache.org/plugins/maven-install-plugin/
[30]: https://maven.apache.org/plugins/maven-resources-plugin/
[31]: https://maven.apache.org/plugins/maven-site-plugin/
[32]: http://docs.sonarqube.org/display/PLUG/Plugin+Library/sonar-scanner-maven/sonar-maven-plugin
[33]: http://www.gnu.org/licenses/lgpl.txt
[34]: https://maven.apache.org/plugins/maven-toolchains-plugin/
[35]: https://maven.apache.org/plugins/maven-compiler-plugin/
[36]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[37]: https://www.mojohaus.org/flatten-maven-plugin/
[38]: https://www.mojohaus.org/exec-maven-plugin
[39]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[40]: http://www.apache.org/licenses/LICENSE-2.0.txt
[41]: https://maven.apache.org/surefire/maven-surefire-plugin/
[42]: https://www.mojohaus.org/versions/versions-maven-plugin/
[43]: https://basepom.github.io/duplicate-finder-maven-plugin
[44]: http://www.apache.org/licenses/LICENSE-2.0.html
[45]: https://maven.apache.org/plugins/maven-artifact-plugin/
[46]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[47]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[48]: https://www.eclipse.org/legal/epl-2.0/
[49]: https://github.com/exasol/quality-summarizer-maven-plugin/
[50]: https://github.com/exasol/quality-summarizer-maven-plugin/blob/main/LICENSE
[51]: https://github.com/exasol/error-code-crawler-maven-plugin/
[52]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[53]: https://github.com/git-commit-id/git-commit-id-maven-plugin
[54]: http://www.gnu.org/licenses/lgpl-3.0.txt
[55]: https://github.com/exasol/project-keeper/
[56]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[57]: https://maven.apache.org/plugins/maven-shade-plugin/
[58]: https://github.com/itsallcode/openfasttrace-maven-plugin
[59]: https://www.gnu.org/licenses/gpl-3.0.html
