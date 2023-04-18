<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                                           | License                                                                                                        |
| ---------------------------------------------------- | -------------------------------------------------------------------------------------------------------------- |
| [AWS Java SDK :: Services :: Amazon CloudWatch][0]   | [Apache License, Version 2.0][1]                                                                               |
| [AWS Java SDK :: Services :: AWS Secrets Manager][0] | [Apache License, Version 2.0][1]                                                                               |
| [EXASolution JDBC Driver][2]                         | [EXAClient License][3]                                                                                         |
| [error-reporting-java][4]                            | [MIT License][5]                                                                                               |
| [AWS Lambda Java Core Library][6]                    | [Apache License, Version 2.0][1]                                                                               |
| [AWS Lambda Java Events Library][6]                  | [Apache License, Version 2.0][1]                                                                               |
| [AWS Lambda Java Log4j 2.x Libraries][6]             | [Apache License, Version 2.0][1]                                                                               |
| [Apache Log4j API][7]                                | [Apache License, Version 2.0][8]                                                                               |
| [Apache Log4j Core][9]                               | [Apache License, Version 2.0][8]                                                                               |
| [Apache Log4j SLF4J Binding][10]                     | [Apache License, Version 2.0][8]                                                                               |
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
| [mockito-core][26]                              | [The MIT License][27]             |
| [Testcontainers :: Localstack][24]              | [MIT][25]                         |
| [AWS Java SDK for Amazon S3][0]                 | [Apache License, Version 2.0][1]  |
| [EqualsVerifier | release normal jar][28]       | [Apache License, Version 2.0][8]  |

## Runtime Dependencies

| Dependency            | License                                                                                                        |
| --------------------- | -------------------------------------------------------------------------------------------------------------- |
| [Eclipse Parsson][29] | [Eclipse Public License 2.0][12]; [GNU General Public License, version 2 with the GNU Classpath Exception][13] |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][30]                       | [GNU LGPL 3][31]                               |
| [Apache Maven Compiler Plugin][32]                      | [Apache-2.0][8]                                |
| [Apache Maven Enforcer Plugin][33]                      | [Apache-2.0][8]                                |
| [Maven Flatten Plugin][34]                              | [Apache Software Licenese][8]                  |
| [Exec Maven Plugin][35]                                 | [Apache License 2][8]                          |
| [Project keeper maven plugin][36]                       | [The MIT License][37]                          |
| [Apache Maven Shade Plugin][38]                         | [Apache License, Version 2.0][8]               |
| [OpenFastTrace Maven Plugin][39]                        | [GNU General Public License v3.0][40]          |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][41] | [ASL2][42]                                     |
| [Maven Surefire Plugin][43]                             | [Apache-2.0][8]                                |
| [Versions Maven Plugin][44]                             | [Apache License, Version 2.0][8]               |
| [duplicate-finder-maven-plugin Maven Mojo][45]          | [Apache License 2.0][46]                       |
| [Maven Failsafe Plugin][47]                             | [Apache-2.0][8]                                |
| [JaCoCo :: Maven Plugin][48]                            | [Eclipse Public License 2.0][49]               |
| [error-code-crawler-maven-plugin][50]                   | [MIT License][51]                              |
| [Reproducible Build Maven Plugin][52]                   | [Apache 2.0][42]                               |
| [Maven Clean Plugin][53]                                | [The Apache Software License, Version 2.0][42] |
| [Maven Resources Plugin][54]                            | [The Apache Software License, Version 2.0][42] |
| [Maven JAR Plugin][55]                                  | [The Apache Software License, Version 2.0][42] |
| [Maven Install Plugin][56]                              | [The Apache Software License, Version 2.0][42] |
| [Maven Deploy Plugin][57]                               | [The Apache Software License, Version 2.0][42] |
| [Maven Site Plugin 3][58]                               | [The Apache Software License, Version 2.0][42] |

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
[24]: https://testcontainers.org
[25]: http://opensource.org/licenses/MIT
[26]: https://github.com/mockito/mockito
[27]: https://github.com/mockito/mockito/blob/main/LICENSE
[28]: https://www.jqno.nl/equalsverifier
[29]: https://github.com/eclipse-ee4j/parsson
[30]: http://sonarsource.github.io/sonar-scanner-maven/
[31]: http://www.gnu.org/licenses/lgpl.txt
[32]: https://maven.apache.org/plugins/maven-compiler-plugin/
[33]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[34]: https://www.mojohaus.org/flatten-maven-plugin/
[35]: https://www.mojohaus.org/exec-maven-plugin
[36]: https://github.com/exasol/project-keeper/
[37]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[38]: https://maven.apache.org/plugins/maven-shade-plugin/
[39]: https://github.com/itsallcode/openfasttrace-maven-plugin
[40]: https://www.gnu.org/licenses/gpl-3.0.html
[41]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[42]: http://www.apache.org/licenses/LICENSE-2.0.txt
[43]: https://maven.apache.org/surefire/maven-surefire-plugin/
[44]: https://www.mojohaus.org/versions/versions-maven-plugin/
[45]: https://github.com/basepom/duplicate-finder-maven-plugin
[46]: http://www.apache.org/licenses/LICENSE-2.0.html
[47]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[48]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[49]: https://www.eclipse.org/legal/epl-2.0/
[50]: https://github.com/exasol/error-code-crawler-maven-plugin/
[51]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[52]: http://zlika.github.io/reproducible-build-maven-plugin
[53]: http://maven.apache.org/plugins/maven-clean-plugin/
[54]: http://maven.apache.org/plugins/maven-resources-plugin/
[55]: http://maven.apache.org/plugins/maven-jar-plugin/
[56]: http://maven.apache.org/plugins/maven-install-plugin/
[57]: http://maven.apache.org/plugins/maven-deploy-plugin/
[58]: http://maven.apache.org/plugins/maven-site-plugin/
