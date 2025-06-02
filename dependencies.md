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
| [EqualsVerifier \| release normal jar][28]      | [Apache License, Version 2.0][8]  |

## Runtime Dependencies

| Dependency            | License                                                                                                        |
| --------------------- | -------------------------------------------------------------------------------------------------------------- |
| [Eclipse Parsson][29] | [Eclipse Public License 2.0][12]; [GNU General Public License, version 2 with the GNU Classpath Exception][13] |

## Plugin Dependencies

| Dependency                                              | License                                     |
| ------------------------------------------------------- | ------------------------------------------- |
| [Apache Maven Clean Plugin][30]                         | [Apache-2.0][8]                             |
| [Apache Maven Install Plugin][31]                       | [Apache-2.0][8]                             |
| [Apache Maven Resources Plugin][32]                     | [Apache-2.0][8]                             |
| [Apache Maven Site Plugin][33]                          | [Apache-2.0][8]                             |
| [SonarQube Scanner for Maven][34]                       | [GNU LGPL 3][35]                            |
| [Apache Maven Toolchains Plugin][36]                    | [Apache-2.0][8]                             |
| [Apache Maven Compiler Plugin][37]                      | [Apache-2.0][8]                             |
| [Apache Maven Enforcer Plugin][38]                      | [Apache-2.0][8]                             |
| [Maven Flatten Plugin][39]                              | [Apache Software Licenese][8]               |
| [Exec Maven Plugin][40]                                 | [Apache License 2][8]                       |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][41] | [ASL2][42]                                  |
| [Maven Surefire Plugin][43]                             | [Apache-2.0][8]                             |
| [Versions Maven Plugin][44]                             | [Apache License, Version 2.0][8]            |
| [duplicate-finder-maven-plugin Maven Mojo][45]          | [Apache License 2.0][46]                    |
| [Apache Maven Artifact Plugin][47]                      | [Apache-2.0][8]                             |
| [Maven Failsafe Plugin][48]                             | [Apache-2.0][8]                             |
| [JaCoCo :: Maven Plugin][49]                            | [EPL-2.0][50]                               |
| [Quality Summarizer Maven Plugin][51]                   | [MIT License][52]                           |
| [error-code-crawler-maven-plugin][53]                   | [MIT License][54]                           |
| [Git Commit Id Maven Plugin][55]                        | [GNU Lesser General Public License 3.0][56] |
| [Project Keeper Maven plugin][57]                       | [The MIT License][58]                       |
| [Apache Maven Shade Plugin][59]                         | [Apache License, Version 2.0][8]            |
| [OpenFastTrace Maven Plugin][60]                        | [GNU General Public License v3.0][61]       |

[0]: https://aws.amazon.com/sdkforjava
[1]: https://aws.amazon.com/apache2.0
[2]: http://www.exasol.com/
[3]: https://repo1.maven.org/maven2/com/exasol/exasol-jdbc/25.2.3/exasol-jdbc-25.2.3-license.txt
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
[34]: http://docs.sonarqube.org/display/PLUG/Plugin+Library/sonar-scanner-maven/sonar-maven-plugin
[35]: http://www.gnu.org/licenses/lgpl.txt
[36]: https://maven.apache.org/plugins/maven-toolchains-plugin/
[37]: https://maven.apache.org/plugins/maven-compiler-plugin/
[38]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[39]: https://www.mojohaus.org/flatten-maven-plugin/
[40]: https://www.mojohaus.org/exec-maven-plugin
[41]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[42]: http://www.apache.org/licenses/LICENSE-2.0.txt
[43]: https://maven.apache.org/surefire/maven-surefire-plugin/
[44]: https://www.mojohaus.org/versions/versions-maven-plugin/
[45]: https://basepom.github.io/duplicate-finder-maven-plugin
[46]: http://www.apache.org/licenses/LICENSE-2.0.html
[47]: https://maven.apache.org/plugins/maven-artifact-plugin/
[48]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[49]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[50]: https://www.eclipse.org/legal/epl-2.0/
[51]: https://github.com/exasol/quality-summarizer-maven-plugin/
[52]: https://github.com/exasol/quality-summarizer-maven-plugin/blob/main/LICENSE
[53]: https://github.com/exasol/error-code-crawler-maven-plugin/
[54]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[55]: https://github.com/git-commit-id/git-commit-id-maven-plugin
[56]: http://www.gnu.org/licenses/lgpl-3.0.txt
[57]: https://github.com/exasol/project-keeper/
[58]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[59]: https://maven.apache.org/plugins/maven-shade-plugin/
[60]: https://github.com/itsallcode/openfasttrace-maven-plugin
[61]: https://www.gnu.org/licenses/gpl-3.0.html
