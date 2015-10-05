# JavaAuth Example Plugin

This authorization plugin is based on HTTP basic authentication.
The purpose of this plugin is to make sure, that a Java based plugin works as well.

## Usage

See the [plugin configuration file](https://github.com/mesosphere/marathon-example-plugins/blob/master/javaauth/src/main/resources/mesosphere/marathon/example/plugin/javaauth/plugin-conf.json).
It allows access if username and password are the same with this permissions:

- Creation is always allowed
- View is always allowed
- Update is allowed only, if the username is ernie
- Deletion is allowed only on path /test (recursively)
- KillTask is not allowed
