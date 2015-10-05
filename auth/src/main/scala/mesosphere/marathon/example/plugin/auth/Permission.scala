package mesosphere.marathon.example.plugin.auth

import mesosphere.marathon.plugin
import mesosphere.marathon.plugin.auth._
import play.api.libs.functional.syntax._
import play.api.libs.json._

trait Permission[A] {
  def eligible[T](action: AuthorizedAction[T]): Boolean
  def isAllowed(resource: A): Boolean
}

object Permission {

  private def actionByName(name: String): AuthorizedAction[plugin.PathId] = name match {
    case "create" => CreateAppOrGroup
    case "update" => UpdateAppOrGroup
    case "delete" => DeleteAppOrGroup
    case "view" => ViewAppOrGroup
    case "killTask" => KillTask
  }

  implicit lazy val permissionReads: Reads[PathPermission] = (
      (__ \ "allowed").read[String].map(actionByName) ~
      (__ \ "on").read[String]
    )(PathPermission)

}

case class PathPermission(allowed: AuthorizedAction[plugin.PathId], on: String) extends Permission[plugin.PathId] {
  override def eligible[T](requested: AuthorizedAction[T]): Boolean = requested == allowed
  override def isAllowed(resource: plugin.PathId): Boolean = resource.toString().startsWith(on)
  override def toString: String = s"Permission(allow: $allowed, on: $on)"
}

