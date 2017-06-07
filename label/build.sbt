name := "label-plugin"

version := "1.0"

resolvers += "Mesosphere Public Repo" at "http://downloads.mesosphere.io/maven"

libraryDependencies ++= Seq(
  "mesosphere.marathon" %% "plugin-interface" % "1.4.0-RC9" % "provided",
  "log4j" % "log4j" % "1.2.17" % "provided",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test"
)
