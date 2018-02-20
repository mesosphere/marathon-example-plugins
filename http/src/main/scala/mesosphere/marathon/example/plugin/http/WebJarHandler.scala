package mesosphere.marathon.example.plugin.http

import mesosphere.marathon.plugin.http.{HttpRequest, HttpRequestHandlerBase, HttpResponse}

class WebJarHandler extends HttpRequestHandlerBase {

  override def serve(request: HttpRequest, response: HttpResponse): Unit = request.method match {
    case "GET" => serveResource("/META-INF/resources/webjars/static/" + request.requestPath, response)
  }
}
