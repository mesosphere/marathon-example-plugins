package mesosphere.marathon.example.plugin.auth

import mesosphere.marathon.plugin.RunSpec
import mesosphere.marathon.plugin.Group
import mesosphere.marathon.plugin.auth._
import play.api.libs.functional.syntax._
import play.api.libs.json._

trait Permission {
  def eligible[R](action: AuthorizedAction[R]): Boolean
  def isAllowed[R](resource: R): Boolean
}

object Permission {

  private def actionByName(name: String): Seq[AuthorizedAction[_]] = name match {
    case "create" => Seq(CreateRunSpec, CreateGroup)
    case "update" => Seq(UpdateRunSpec, UpdateGroup)
    case "delete" => Seq(DeleteRunSpec, DeleteGroup)
    case "view" => Seq(ViewRunSpec, ViewGroup)
  }

  implicit lazy val permissionReads: Reads[PathPermission] = (
      (__ \ "allowed").read[String].map(actionByName) ~
      (__ \ "on").read[String]
    )(PathPermission)

}

case class PathPermission(allowed: Seq[AuthorizedAction[_]], on: String) extends Permission {

  override def eligible[R](requested: AuthorizedAction[R]): Boolean = allowed.contains(requested)
  override def isAllowed[R](resource: R): Boolean = resource match {
    case app: RunSpec => app.id.toString.startsWith(on)
    case group: Group => group.id.toString.startsWith(on)
    case _ => false
  }
  override def toString: String = s"Permission(allow: $allowed, on: $on)"
}

