# JavaAuth Example Plugin

This authorization plugin is based on HTTP basic authentication.
The purpose of this plugin is to make sure, that a java based plugin works as well.

## Usage

See the plugin configuration file.
It allows access if username and password are the same with this permissions:

- Creation is always allowed
- View is always allowed
- Update is allowed only, if the username is ernie
- Deletion is allowed only on path /test (recursively)
- KillTask is not allowed

