package mesosphere.marathon.example.plugin.executorid

import com.typesafe.scalalogging.StrictLogging
import mesosphere.marathon.plugin.{ApplicationSpec, PodSpec}
import mesosphere.marathon.plugin.plugin.PluginConfiguration
import mesosphere.marathon.plugin.task.RunSpecTaskProcessor
import org.apache.mesos.Protos._
import play.api.libs.json.JsObject

import scala.collection.JavaConverters._

class ExecutorIdExtenderPlugin extends RunSpecTaskProcessor with PluginConfiguration with StrictLogging {

  val ExecutorIdLabel = "MARATHON_EXECUTOR_ID"

  override def taskInfo(appSpec: ApplicationSpec, builder: TaskInfo.Builder): Unit = {
    // If custom executor is used
    if (builder.hasExecutor && builder.getExecutor.hasCommand) {
      val labels = builder.getLabels.getLabelsList.asScala

      // ... and there is MARATHON_EXECUTOR_ID label set
      labels.find(_.getKey == ExecutorIdLabel).foreach {label =>
        // Set the executorID from the MARATHON_EXECUTOR_ID label
        val executorId = label.getValue
        val executorBuilder = builder.getExecutor.toBuilder
        executorBuilder.setExecutorId(ExecutorID.newBuilder.setValue(executorId))

        // An executor id of the executor to launch this application. Note that all application sharing the same
        // executor id will share the same executor instance allowing to save resources. The downfall is that all
        // the apps started with the same executo id must have identical `TaskInfo.ExecutorInfo`. Among other things that
        // means environment variables must be identical. Since marathon would automatically generate per-task environment
        // variables like `MARATHON_APP_VERSION`, `MESOS_TASK_ID` or `PORTx` this will not work.
        // For this reason we just remove all the environment variables. It is possible to be more selective and remove
        // only those environment variables that change from task to task but that's too much hustle for this simple plugin.
        val commandBuilder = executorBuilder.getCommand.toBuilder
        commandBuilder.clearEnvironment()
        executorBuilder.setCommand(commandBuilder)

        builder.setExecutor(executorBuilder)
      }
    }
  }

  override def taskGroup(podSpec: PodSpec, executor: ExecutorInfo.Builder, taskGroup: TaskGroupInfo.Builder): Unit = {}

  override def initialize(marathonInfo: Map[String, Any], configuration: JsObject): Unit = {
    logger.info(s"ExecutorIdExtenderPlugin successfully initialized")
  }
}
