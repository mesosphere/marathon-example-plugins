package mesosphere.marathon.example.plugin.executorid

import com.typesafe.scalalogging.StrictLogging
import org.apache.mesos.Protos.Environment.Variable
import org.apache.mesos.Protos._
import org.scalatest.{GivenWhenThen, Matchers, WordSpec}

class ExecutorIdExtenderPluginTest extends WordSpec with Matchers with GivenWhenThen with StrictLogging {

  "Given an MARATHON_EXECUTOR_ID label an executorID should be injected" in {
    val f = new Fixture

    Given("a TaskInfo with a MARATHON_EXECUTOR_ID label")
    val taskInfo = TaskInfo.newBuilder.
      setExecutor(ExecutorInfo.newBuilder.
          setCommand(CommandInfo.newBuilder.
            setEnvironment(Environment.newBuilder.addVariables(
                Variable.newBuilder.setName("foo").setValue("bar")
            )
          )).
        setExecutorId(ExecutorID.newBuilder.setValue("task.12345"))
      ).
      setLabels(Labels.newBuilder.addLabels(Label.newBuilder.
        setKey(f.plugin.ExecutorIdLabel)
          .setValue("customer-executor-id")
      ))

    When("handled by the plugin")
    f.plugin.taskInfo(null, taskInfo)

    Then("ExecutorInfo.ExecutorId should be changed")
    taskInfo.getExecutor.getExecutorId.getValue shouldBe "customer-executor-id"

    And("Environment variables should be removed")
    taskInfo.getExecutor.getCommand.getEnvironment.getVariablesCount shouldBe 0
  }

  "Given no MARATHON_EXECUTOR_ID label an executorID should be untouched" in {
    val f = new Fixture

    Given("a TaskInfo with a MARATHON_EXECUTOR_ID label")
    val taskInfo = TaskInfo.newBuilder.
      setExecutor(ExecutorInfo.newBuilder.
        setCommand(CommandInfo.newBuilder.
          setEnvironment(Environment.newBuilder.addVariables(
            Variable.newBuilder.setName("foo").setValue("bar")
          )
          )).
        setExecutorId(ExecutorID.newBuilder.setValue("task.12345"))
      ).
      setLabels(Labels.newBuilder.addLabels(Label.newBuilder.
        setKey("baz")
        .setValue("wof")
      ))

    When("handled by the plugin")
    f.plugin.taskInfo(null, taskInfo)

    Then("ExecutorInfo.ExecutorId should stay the same")
    taskInfo.getExecutor.getExecutorId.getValue shouldBe "task.12345"

    And("environment variables should be kept")
    taskInfo.getExecutor.getCommand.getEnvironment.getVariablesCount shouldBe 1
  }

  class Fixture {
    val plugin = new ExecutorIdExtenderPlugin()
  }
}
