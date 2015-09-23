package mesosphere.marathon.example.plugin.auth

import mesosphere.marathon.plugin.auth._
import mesosphere.marathon.plugin.http._

class ExampleAuthorizer extends Authorizer {

  override def handleNotAuthorized(principal: Identity, request: HttpRequest, response: HttpResponse): Unit = {
    response.status(403)
    response.body("application/json", s"""{"problem": "Not Authorized to perform this action!"}""".getBytes("UTF-8"))
  }

  override def isAuthorized[Resource](principal: Identity,
                                      action: AuthorizedAction[Resource],
                                      resource: Resource): Boolean = {
    principal match {
      case identity: ExampleIdentity => identity.isAllowed(action, resource)
      case _                      => false
    }
  }

}
