# Http Example Plugin

This http handler plugin allows the handling of basic http request/response cycles.

## Usage

Start up Marathon according to the example project [README.md](../README.md) file, using the [plugin configuration file](https://github.com/mesosphere/marathon-example-plugins/blob/master/http/src/main/resources/mesosphere/marathon/example/plugin/http/plugin-conf.json) configuration file.  This plugin example implements the [HttpRequestHandlerBase](https://github.com/mesosphere/marathon/blob/master/plugin-interface/src/main/scala/mesosphere/marathon/plugin/http/HttpRequestHandlerBase.scala) plugin with [WebJarHandler](https://github.com/mesosphere/marathon-example-plugins/blob/master/http/src/main/scala/mesosphere/marathon/example/plugin/http/WebJarHandler.scala).  The implementation of the WebJarHandler class will serve static content that is placed under the [/META-INF/resources/webjars/static](https://github.com/mesosphere/marathon-example-plugins/blob/master/http/src/main/scala/mesosphere/marathon/example/plugin/http/WebJarHandler.scala#L11) folder.

It will serve the contents via: http://localhost:8080/v2/plugins/webjar/hello.txt where webjar is it's pluginid defined in the [plugin configuration](https://github.com/mesosphere/marathon-example-plugins/blob/master/http/src/main/resources/mesosphere/marathon/example/plugin/http/plugin-conf.json#L3).
