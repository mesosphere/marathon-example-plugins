package mesosphere.marathon.example.plugin.auth

import mesosphere.marathon.plugin.auth._
import org.apache.log4j.Logger
import play.api.libs.functional.syntax._
import play.api.libs.json._


case class ExampleIdentity(username: String, password: String, permissions: Seq[Permission]) extends Identity {
  val log = Logger.getLogger(classOf[ExampleIdentity])

  def isAllowed[R](action: AuthorizedAction[R], resource: R): Boolean = {
    val permit = permissions.find { permission =>
      permission.eligible(action) && permission.isAllowed(resource)
    }
    permit match {
      case Some(p) => log.info(s"Found permit: $p")
      case None    => log.error(s"$username is not allowed for action $action on resource $resource")
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

