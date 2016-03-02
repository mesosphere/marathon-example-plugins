
organization in Global := "mesosphere.marathon"

name in Global := "example-plugins"

scalaVersion in Global := "2.11.7"

resolvers += Resolver.sonatypeRepo("releases")

lazy val plugins = project.in(file(".")).dependsOn(auth).dependsOn(javaauth).dependsOn(http)

lazy val auth = project

lazy val javaauth = project

lazy val http = project

packAutoSettings

packExcludeJars := Seq("scala-.*\\.jar")


