# Example plugins for Marathon

## Marathon Plugin Dependency

The Marathon plugin interface is needed to compile this package.

## Package

To build the package run this command:
`sbt clean pack`
This will compile and package all plugins.
The resulting jars with all dependencies are put into the directory: `target/pack/lib`
This directory can be used directly as plugin directory for Marathon.

## Plugins 

### Plugin auth

Example Authentication and Authorization plugin (Scala based).
See [README.md](https://github.com/mesosphere/marathon-example-plugins/blob/master/auth/README.md) in the auth plugin directory.

### Plugin javaauth

Example Authentication and Authorization plugin (Java based).
See [README.md](https://github.com/mesosphere/marathon-example-plugins/blob/master/javaauth/README.md) in the javaauth directory.
