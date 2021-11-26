name := "PublishAuthenticationCode"

version := "0.1"

scalaVersion := "2.13.7"

resolvers += "maven.elmakers.com" at "https://maven.elmakers.com/repository/"
resolvers += Resolver.bintrayIvyRepo("com.eed3si9n", "sbt-plugins")
libraryDependencies += "org.spigotmc" % "spigot-api" % "1.17.1-R0.1-SNAPSHOT"