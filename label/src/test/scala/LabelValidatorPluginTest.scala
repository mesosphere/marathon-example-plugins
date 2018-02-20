package mesosphere.marathon.example.plugin.label


import mesosphere.marathon.plugin._
import org.scalatest.{FlatSpec, Matchers}


class LabelValidatorPluginTest extends FlatSpec with Matchers {

  "plugin with pod spec" should "work" in {
    val pod = FakePodSpec(labels = Map("BOGUS_LABEL" -> "foo"))
    val labelValidator = new LabelValidatorPlugin()
    labelValidator.apply(pod).isSuccess shouldBe true
  }

  "plugin with app spec no labels" should "work" in {
    val app = FakeAppSpec(labels = Map("BOGUS_LABEL" -> "foo"))
    val labelValidator = new LabelValidatorPlugin()
    labelValidator.apply(app).isSuccess shouldBe true
  }

  "plugin with app spec with restricted label" should "fail" in {
    val app = FakeAppSpec(labels = Map("DCOS_PACKAGE_FRAMEWORK_NAME" -> "foo"))
    val labelValidator = new LabelValidatorPlugin()
    labelValidator.apply(app).isFailure shouldBe true
  }
}

case class FakePath(path: List[ String ] = List("fake")) extends PathId {
  override def toString: String = path.toSeq.reverse.mkString(".")
}

case class FakeAppSpec(
    id: PathId = FakePath(),
    user: Option[String] = None,
    env: Map[String, EnvVarValue] = Map.empty,
    labels: Map[String, String] = Map.empty,
    acceptedResourceRoles: Set[String] = Set.empty,
    secrets: Map[String, Secret] = Map.empty,
    networks: Seq[NetworkSpec] = Seq.empty,
    volumeMounts: Seq[VolumeMountSpec] = Seq.empty,
    volumes: Seq[VolumeSpec] = Seq.empty
  ) extends ApplicationSpec


case class FakePodSpec(
    id: PathId = FakePath(),
    acceptedResourceRoles: Set[String] = Set.empty,
    secrets: Map[String, Secret] = Map.empty,
    env: Map[String, EnvVarValue] = Map.empty,
    containers: Seq[ContainerSpec] = Seq.empty,
    networks: Seq[NetworkSpec] = Seq.empty,
    labels: Map[String, String] = Map.empty,
    volumeMounts: Seq[VolumeMountSpec] = Seq.empty,
    volumes: Seq[VolumeSpec] = Seq.empty
  ) extends PodSpec
