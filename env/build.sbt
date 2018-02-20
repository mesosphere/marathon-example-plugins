name := "env-plugin"

version := "1.0"

resolvers += "Mesosphere Public Repo" at "http://downloads.mesosphere.io/maven"

libraryDependencies ++= Seq(
  "mesosphere.marathon" %% "plugin-interface" % "1.6.325" % "provided",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)
