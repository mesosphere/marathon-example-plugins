# Example plugins for Marathon

## Marathon Plugin Dependency

The Marathon plugin interface is needed to compile this package.

## Package

To build the package run this command:
`sbt clean pack`
This will compile and package all plugins.
The resulting jars with all dependencies are put into the directory: `target/pack/lib`.
This directory can be used directly as plugin directory for Marathon.

# Using a Plugin
1. Run `sbt clean pack` in the repository's root directory.
2. Locate the Plugin configuration file (look at the Plugin's README.md
   for a hint)).
3. Start Marathon with the following flags: `--plugin_dir target/pack/lib --plugin_conf <path_to_the_plugin_config_file>`

## Plugins

### auth

Example Authentication and Authorization Plugin (Scala based).
See [README.md](https://github.com/mesosphere/marathon-example-plugins/blob/master/auth/README.md) in the auth plugin directory.

### javaauth

Example Authentication and Authorization Plugin (Java based).
See [README.md](https://github.com/mesosphere/marathon-example-plugins/blob/master/javaauth/README.md) in the javaauth directory.

### env

Example Environment Variables Configuration.
See [README.md](https://github.com/mesosphere/marathon-example-plugins/blob/master/env/README.md) in the env plugin directory.

### label

Example validation of runspec for specific labels.
See [README.md](https://github.com/mesosphere/marathon-example-plugins/blob/master/label/README.md) in the label plugin directory.
