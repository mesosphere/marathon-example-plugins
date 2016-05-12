package mesosphere.marathon.example.plugin.auth

import mesosphere.marathon.plugin.auth._
import mesosphere.marathon.plugin.http._

class ExampleAuthorizer extends Authorizer {

  override def handleNotAuthorized(principal: Identity, response: HttpResponse): Unit = {
    response.status(403)
    response.body("application/json", s"""{"message": "Not Authorized to perform this action!"}""".getBytes("UTF-8"))
  }

  override def isAuthorized[R](principal: Identity,
                                      action: AuthorizedAction[R],
                                      resource: R): Boolean = {
    principal match {
      case identity: ExampleIdentity => identity.isAllowed(action, resource)
      case _                      => false
    }
  }
}
