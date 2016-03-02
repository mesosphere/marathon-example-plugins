package mesosphere.marathon.example.plugin.http

import mesosphere.marathon.plugin.http.{HttpRequest, HttpRequestHandlerBase, HttpResponse}
import org.slf4j.LoggerFactory

class WebJarHandler extends HttpRequestHandlerBase {

  val log = LoggerFactory.getLogger(getClass)

  override def serve(request: HttpRequest, response: HttpResponse): Unit = request.method match {
    case "GET" => serveResource("/META-INF/resources/webjars/static/" + request.requestPath, response)
  }
}
