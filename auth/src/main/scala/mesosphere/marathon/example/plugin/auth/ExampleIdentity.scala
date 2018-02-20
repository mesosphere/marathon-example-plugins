package mesosphere.marathon.example.plugin.auth

import com.typesafe.scalalogging.StrictLogging
import mesosphere.marathon.plugin.auth._
import play.api.libs.functional.syntax._
import play.api.libs.json._


case class ExampleIdentity(username: String, password: String, permissions: Seq[Permission]) extends Identity with StrictLogging {

  def isAllowed[R](action: AuthorizedAction[R], resource: R): Boolean = {
    val permit = permissions.find { permission =>
      permission.eligible(action) && permission.isAllowed(resource)
    }
    permit match {
      case Some(p) => logger.info(s"Found permit: $p")
      case None    => logger.error(s"$username is not allowed for action $action on resource $resource")
    }
    permit.isDefined
  }
}

object ExampleIdentity {
  implicit val identityRead: Reads[ExampleIdentity] = (
    (__ \ "user").read[String] ~
    (__ \ "password").read[String] ~
    (__ \ "permissions").read[Seq[PathPermission]]
  ) ((name, pass, permissions) => ExampleIdentity(name, pass, permissions))
}

