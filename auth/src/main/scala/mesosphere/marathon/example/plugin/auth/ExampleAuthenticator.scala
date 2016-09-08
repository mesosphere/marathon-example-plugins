package mesosphere.marathon.example.plugin.auth

import java.util.Base64

import mesosphere.marathon.plugin.auth.{ Authenticator, Identity }
import mesosphere.marathon.plugin.http.{ HttpRequest, HttpResponse }
import mesosphere.marathon.plugin.plugin.PluginConfiguration
import play.api.libs.json.JsObject

import scala.concurrent.Future

class ExampleAuthenticator extends Authenticator with PluginConfiguration {

  import scala.concurrent.ExecutionContext.Implicits.global

  override def handleNotAuthenticated(request: HttpRequest, response: HttpResponse): Unit = {
    response.status(401)
    response.header("WWW-Authenticate", """Basic realm="Marathon Example Authentication"""")
    response.body("application/json", """{"message": "Not Authenticated!"}""".getBytes("UTF-8"))
  }

  override def authenticate(request: HttpRequest): Future[Option[Identity]] = Future {

    def basicAuth(header: String): Option[(String, String)] = {
      val BasicAuthRegex = "Basic (.+)".r
      val UserPassRegex = "([^:]+):(.+)".r
      header match {
        case BasicAuthRegex(encoded) =>
          val decoded = new String(Base64.getDecoder.decode(encoded))
          val UserPassRegex(username, password) = decoded
          Some(username->password)
        case _ => None
      }
    }

    for {
      auth <- request.header("Authorization").headOption
      (username, password) <- basicAuth(auth)
      identity <- identities.get(username) if identity.password == password
    } yield identity

  }

  private var identities = Map.empty[String, ExampleIdentity]

  override def initialize(marathonInfo: Map[String, Any], configuration: JsObject): Unit = {
    //read all identities from the configuration
    identities = (configuration \ "users").as[Seq[ExampleIdentity]].map(id => id.username -> id).toMap
  }
}
