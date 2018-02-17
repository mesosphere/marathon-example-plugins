package mesosphere.marathon.example.plugin.env

import mesosphere.marathon.plugin.{ApplicationSpec, PodSpec}
import mesosphere.marathon.plugin.task._
import mesosphere.marathon.plugin.plugin.PluginConfiguration
import org.apache.mesos.Protos
import org.apache.mesos.Protos.{ExecutorInfo, TaskGroupInfo, TaskInfo}

class EnvVarExtenderPlugin extends RunSpecTaskProcessor with PluginConfiguration {
  private[env] var envVariables = Map.empty[String, String]

  def initialize(marathonInfo: Map[String, Any], configuration: play.api.libs.json.JsObject): Unit = {
    envVariables = (configuration \ "env").as[Map[String, String]]
  }

  override def taskInfo(appSpec: ApplicationSpec, builder: TaskInfo.Builder): Unit = {
    val envBuilder = builder.getCommand.getEnvironment.toBuilder
    envVariables.foreach {
      case (key, value) =>
        val envVariable = Protos.Environment.Variable.newBuilder()
        envVariable.setName(key)
        envVariable.setValue(value)
        envBuilder.addVariables(envVariable)
    }
    val commandBuilder = builder.getCommand.toBuilder
    commandBuilder.setEnvironment(envBuilder)
    builder.setCommand(commandBuilder)
  }

  override def taskGroup(podSpec: PodSpec, executor: ExecutorInfo.Builder, taskGroup: TaskGroupInfo.Builder): Unit = ???
}
