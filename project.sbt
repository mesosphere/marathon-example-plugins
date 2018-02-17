
organization in Global := "mesosphere.marathon"

name in Global := "example-plugins"

scalaVersion in Global := "2.12.4"

resolvers += Resolver.sonatypeRepo("releases")

lazy val plugins = project.in(file(".")).dependsOn(auth).dependsOn(javaauth).dependsOn(http).dependsOn(env).dependsOn(label)

lazy val auth = project

lazy val javaauth = project

lazy val http = project

lazy val env = project

lazy val label = project

packAutoSettings

packExcludeJars := Seq("scala-.*\\.jar")
