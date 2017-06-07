package mesosphere.marathon.example.plugin.label


import com.wix.accord._
import mesosphere.marathon.plugin.validation.RunSpecValidator
import mesosphere.marathon.plugin.{ApplicationSpec, RunSpec}


class LabelValidatorPlugin extends RunSpecValidator {
  override def apply(runSpec: RunSpec): Result = runSpec match {
    case app: ApplicationSpec => DCOSServiceLabelValidator.appIsValid(app)
    case _ => Success
  }
}

/**
  * DCOS uses the labels DCOS_PACKAGE_FRAMEWORK_NAME and DCOS_SERVICE_NAME for frameworks and services.  This validator
  * rejects those labels.  IF they exist we fail, otherwise we succeed.
  * This ensures the marathon that uses this plugin will never launch what is intended to be a DCOS service.
  */
object DCOSServiceLabelValidator {

  val forbiddenLabels = List("DCOS_PACKAGE_FRAMEWORK_NAME", "DCOS_SERVICE_NAME")

  protected[ label ] lazy val appIsValid = new Validator[ ApplicationSpec ] {
    def apply(app: ApplicationSpec): Result = {
      if (app.labels.keySet.exists(forbiddenLabels.contains))
        Failure(Set(RuleViolation(app.labels, s"An App label contains one or more DCOS LABELs ${forbiddenLabels} which is restricted", None)))
      else Success
    }
  }
}
