# Auth Example Plugin

This authorization plugin is based on HTTP basic authentication.

## Usage

See the [plugin configuration file](https://github.com/mesosphere/marathon-example-plugins/blob/master/auth/src/main/resources/mesosphere/marathon/example/plugin/auth/plugin-conf.json).
You can configure username + password + permissions for all users.
The permissions are based on the app/group path on which an action is
performed.
