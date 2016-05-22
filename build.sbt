name := "play-java-spring"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaCore,
  javaWs % "test",
  "com.h2database" % "h2" % "1.4.181",
//  "mysql" % "mysql-connector-java" % "5.1.38",
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "org.springframework" % "spring-context" % "4.1.1.RELEASE",
  "org.springframework" % "spring-jdbc" % "4.1.1.RELEASE",
  "org.springframework" % "spring-test" % "4.1.1.RELEASE" % "test",
  "org.webjars" % "bootstrap" % "3.0.3",
  "org.mockito" % "mockito-core" % "1.9.5"
)


lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .enablePlugins(SbtWeb)

