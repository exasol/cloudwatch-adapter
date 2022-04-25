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
| [SonarQube Scanner for Maven][44]                       | [GNU LGPL 3][45]                               |
| [Apache Maven Compiler Plugin][46]                      | [Apache License, Version 2.0][15]              |
| [Apache Maven Enforcer Plugin][48]                      | [Apache License, Version 2.0][15]              |
| [Maven Flatten Plugin][50]                              | [Apache Software Licenese][51]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][52] | [ASL2][51]                                     |
| [Reproducible Build Maven Plugin][54]                   | [Apache 2.0][51]                               |
| [Maven Surefire Plugin][56]                             | [Apache License, Version 2.0][15]              |
| [Versions Maven Plugin][58]                             | [Apache License, Version 2.0][15]              |
| [Project keeper maven plugin][60]                       | [The MIT License][61]                          |
| [Apache Maven Assembly Plugin][62]                      | [Apache License, Version 2.0][15]              |
| [Apache Maven JAR Plugin][64]                           | [Apache License, Version 2.0][15]              |
| [Exec Maven Plugin][66]                                 | [Apache License 2][51]                         |
| [Artifact reference checker and unifier][68]            | [MIT][7]                                       |
| [Maven Failsafe Plugin][70]                             | [Apache License, Version 2.0][15]              |
| [JaCoCo :: Maven Plugin][72]                            | [Eclipse Public License 2.0][73]               |
| [error-code-crawler-maven-plugin][74]                   | [MIT][7]                                       |
| [Apache Maven Shade Plugin][76]                         | [Apache License, Version 2.0][15]              |
| [OpenFastTrace Maven Plugin][78]                        | [GNU General Public License v3.0][79]          |
| [Maven Clean Plugin][80]                                | [The Apache Software License, Version 2.0][51] |
| [Maven Resources Plugin][82]                            | [The Apache Software License, Version 2.0][51] |
| [Maven Install Plugin][84]                              | [The Apache Software License, Version 2.0][51] |
| [Maven Deploy Plugin][86]                               | [The Apache Software License, Version 2.0][51] |
| [Maven Site Plugin 3][88]                               | [The Apache Software License, Version 2.0][51] |

[6]: https://github.com/exasol/error-reporting-java
[27]: http://www.eclipse.org/legal/epl-v20.html
[20]: https://javaee.github.io/jsonp
[51]: http://www.apache.org/licenses/LICENSE-2.0.txt
[56]: https://maven.apache.org/surefire/maven-surefire-plugin/
[5]: https://www.exasol.com/support/secure/attachment/155343/EXASOL_SDK-7.0.11.tar.gz
[80]: http://maven.apache.org/plugins/maven-clean-plugin/
[0]: https://aws.amazon.com/sdkforjava
[7]: https://opensource.org/licenses/MIT
[36]: https://github.com/mockito/mockito
[66]: http://www.mojohaus.org/exec-maven-plugin
[58]: http://www.mojohaus.org/versions-maven-plugin/
[60]: https://github.com/exasol/project-keeper/
[76]: https://maven.apache.org/plugins/maven-shade-plugin/
[29]: http://opensource.org/licenses/BSD-3-Clause
[46]: https://maven.apache.org/plugins/maven-compiler-plugin/
[21]: https://oss.oracle.com/licenses/CDDL+GPL-1.1
[78]: https://github.com/itsallcode/openfasttrace-maven-plugin
[73]: https://www.eclipse.org/legal/epl-2.0/
[18]: https://logging.apache.org/log4j/2.x/log4j-slf4j18-impl/
[45]: http://www.gnu.org/licenses/lgpl.txt
[72]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[1]: https://aws.amazon.com/apache2.0
[37]: https://github.com/mockito/mockito/blob/main/LICENSE
[54]: http://zlika.github.io/reproducible-build-maven-plugin
[44]: http://sonarsource.github.io/sonar-scanner-maven/
[16]: https://logging.apache.org/log4j/2.x/log4j-core/
[30]: https://junit.org/junit5/
[50]: https://www.mojohaus.org/flatten-maven-plugin/flatten-maven-plugin
[28]: http://hamcrest.org/JavaHamcrest/
[82]: http://maven.apache.org/plugins/maven-resources-plugin/
[68]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[64]: https://maven.apache.org/plugins/maven-jar-plugin/
[14]: https://logging.apache.org/log4j/2.x/log4j-api/
[70]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[24]: https://github.com/exasol/test-db-builder-java
[35]: http://opensource.org/licenses/MIT
[32]: https://github.com/exasol/exasol-testcontainers
[61]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[79]: https://www.gnu.org/licenses/gpl-3.0.html
[15]: https://www.apache.org/licenses/LICENSE-2.0.txt
[42]: https://www.jqno.nl/equalsverifier
[48]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[4]: http://www.exasol.com
[31]: https://www.eclipse.org/legal/epl-v20.html
[84]: http://maven.apache.org/plugins/maven-install-plugin/
[52]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[8]: https://aws.amazon.com/lambda/
[34]: https://testcontainers.org
[26]: https://github.com/itsallcode/junit5-system-extensions
[86]: http://maven.apache.org/plugins/maven-deploy-plugin/
[88]: http://maven.apache.org/plugins/maven-site-plugin/
[74]: https://github.com/exasol/error-code-crawler-maven-plugin
[62]: https://maven.apache.org/plugins/maven-assembly-plugin/
