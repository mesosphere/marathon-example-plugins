# Http Example Plugin

This http handler plugin allows the handling of basic http request/response cycles.

## Usage

See the [plugin configuration file](https://github.com/mesosphere/marathon-example-plugins/blob/master/http/src/main/resources/mesosphere/marathon/example/plugin/http/plugin-conf.json).
The WebHarHandler will serve static content that is placed under the /META-INF/resources/webjars/static folder.
It will serve the content via /plugin id/: http://localhost:8080/v2/plugins/webjar/hello.txt
