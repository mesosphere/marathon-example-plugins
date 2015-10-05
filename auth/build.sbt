name := "auth-plugin"

version := "1.0"

resolvers += "Mesosphere Public Repo" at "http://downloads.mesosphere.io/maven"

libraryDependencies ++= Seq(
  "mesosphere.marathon" %% "plugin-interface" % "0.12.0-SNAPSHOT" % "provided",
  "log4j" % "log4j" % "1.2.17" % "provided"
)

