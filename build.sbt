import scala.language.postfixOps

name := "PublishAuthenticationCode"

version := "0.1"

scalaVersion := "2.13.7"

scalacOptions += "-deprecation"

resolvers += "maven.elmakers.com" at "https://maven.elmakers.com/repository/"
resolvers += Resolver.bintrayIvyRepo("com.eed3si9n", "sbt-plugins")
libraryDependencies ++= Seq(
  "org.spigotmc" % "spigot-api" % "1.17.1-R0.1-SNAPSHOT",
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "com.typesafe.slick" %% "slick-codegen" % "3.3.3",
  "mysql" % "mysql-connector-java" % "8.0.25",
  "org.scalatest" %% "scalatest" % "3.2.9" % Test,
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "org.slf4j" % "slf4j-nop" % "1.7.32",
  "com.h2database" % "h2" % "1.4.200"
)

Compile / sourceGenerators += slick.taskValue

lazy val slick = taskKey[Seq[File]]("Generate Tables.scala")
slick := {
  val dir = (Compile / sourceManaged) value
  val outputDir = dir / "slick"
  val url = "jdbc:mysql://localhost/mydb" // connection info
  val jdbcDriver = "com.mysql.cj.jdbc.Driver"
  val slickDriver = "slick.jdbc.MySQLProfile"
  val pkg = "mydb"
  val user = "root"
  val password = "anypassw0rd"
  val cp = (Compile / dependencyClasspath) value
  val s = streams value

  runner.value.run("slick.codegen.SourceCodeGenerator",
    cp.files,
    Array(slickDriver, jdbcDriver, url, outputDir.getPath, pkg, user, password),
    s.log).failed foreach (sys error _.getMessage)

  val file = outputDir / pkg / "Tables.scala"

  Seq(file)
}