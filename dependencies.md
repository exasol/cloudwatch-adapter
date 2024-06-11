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
| [Apache Log4j SLF4J Binding][10]                     | [Apache-2.0][8]                                                                                                |
| [Jakarta JSON Processing API][11]                    | [Eclipse Public License 2.0][12]; [GNU General Public License, version 2 with the GNU Classpath Exception][13] |
| [Test Database Builder for Java][14]                 | [MIT License][15]                                                                                              |
| [JUnit5 System Extensions][16]                       | [Eclipse Public License v2.0][17]                                                                              |

## Test Dependencies

| Dependency                                      | License                           |
| ----------------------------------------------- | --------------------------------- |
| [Hamcrest][18]                                  | [BSD License 3][19]               |
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
| [SonarQube Scanner for Maven][30]                       | [GNU LGPL 3][31]                      |
| [Apache Maven Toolchains Plugin][32]                    | [Apache-2.0][8]                       |
| [Apache Maven Compiler Plugin][33]                      | [Apache-2.0][8]                       |
| [Apache Maven Enforcer Plugin][34]                      | [Apache-2.0][8]                       |
| [Maven Flatten Plugin][35]                              | [Apache Software Licenese][8]         |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][36] | [ASL2][37]                            |
| [Maven Surefire Plugin][38]                             | [Apache-2.0][8]                       |
| [Versions Maven Plugin][39]                             | [Apache License, Version 2.0][8]      |
| [duplicate-finder-maven-plugin Maven Mojo][40]          | [Apache License 2.0][41]              |
| [Maven Failsafe Plugin][42]                             | [Apache-2.0][8]                       |
| [JaCoCo :: Maven Plugin][43]                            | [EPL-2.0][44]                         |
| [error-code-crawler-maven-plugin][45]                   | [MIT License][46]                     |
| [Reproducible Build Maven Plugin][47]                   | [Apache 2.0][37]                      |
| [Exec Maven Plugin][48]                                 | [Apache License 2][8]                 |
| [Project Keeper Maven plugin][49]                       | [The MIT License][50]                 |
| [Apache Maven Shade Plugin][51]                         | [Apache License, Version 2.0][8]      |
| [OpenFastTrace Maven Plugin][52]                        | [GNU General Public License v3.0][53] |

[0]: https://aws.amazon.com/sdkforjava
[1]: https://aws.amazon.com/apache2.0
[2]: http://www.exasol.com/
[3]: https://repo1.maven.org/maven2/com/exasol/exasol-jdbc/24.1.0/exasol-jdbc-24.1.0-license.txt
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
[19]: http://opensource.org/licenses/BSD-3-Clause
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
[30]: http://sonarsource.github.io/sonar-scanner-maven/
[31]: http://www.gnu.org/licenses/lgpl.txt
[32]: https://maven.apache.org/plugins/maven-toolchains-plugin/
[33]: https://maven.apache.org/plugins/maven-compiler-plugin/
[34]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[35]: https://www.mojohaus.org/flatten-maven-plugin/
[36]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[37]: http://www.apache.org/licenses/LICENSE-2.0.txt
[38]: https://maven.apache.org/surefire/maven-surefire-plugin/
[39]: https://www.mojohaus.org/versions/versions-maven-plugin/
[40]: https://basepom.github.io/duplicate-finder-maven-plugin
[41]: http://www.apache.org/licenses/LICENSE-2.0.html
[42]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[43]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[44]: https://www.eclipse.org/legal/epl-2.0/
[45]: https://github.com/exasol/error-code-crawler-maven-plugin/
[46]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[47]: http://zlika.github.io/reproducible-build-maven-plugin
[48]: https://www.mojohaus.org/exec-maven-plugin
[49]: https://github.com/exasol/project-keeper/
[50]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[51]: https://maven.apache.org/plugins/maven-shade-plugin/
[52]: https://github.com/itsallcode/openfasttrace-maven-plugin
[53]: https://www.gnu.org/licenses/gpl-3.0.html
