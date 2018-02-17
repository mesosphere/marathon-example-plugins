package mesosphere.marathon.example.plugin.env

import mesosphere.marathon.plugin.ApplicationSpec
import org.apache.mesos.Protos
import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.{JsObject, Json}


class EnvVarExtenderPluginTest extends FlatSpec with Matchers {
  "Initialization with a configuration" should "work" in {
    val f = new Fixture
    f.envVarExtender.envVariables should be(Map("foo" -> "bar", "key" -> "value"))
  }

  "Applying the plugin" should "work" in {
    val f = new Fixture
    val runSpec: ApplicationSpec = null
    val builder = Protos.TaskInfo.newBuilder()
    f.envVarExtender.taskInfo(runSpec, builder)
    builder.getCommand.getEnvironment.getVariablesList.get(0).getName should be("foo")
  }

  class Fixture {
    val json =
      """{
        |    "env": {
        |        "foo": "bar",
        |        "key": "value"
        |    }
        |}
      """.stripMargin
    val config = Json.parse(json).as[JsObject]
    val envVarExtender = new EnvVarExtenderPlugin()
    envVarExtender.initialize(Map.empty, config)
  }
}
