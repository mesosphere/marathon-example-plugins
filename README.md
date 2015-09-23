# Example plugins for Marathon

## Marathon Plugin Dependency

The Marathon plugin interface is needed to compile this package.
Checkout Marathon and run `sbt plugin-interface/publishLocal`

## Package

To build the package run this command:
`sbt clean pack`
This will compile and package all plugins.
The resulting jars with all dependencies are put into the directory: `target/pack/lib`
This directory can be used directly as plugin directory for Marathon.

## Plugins 

### Plugin auth

Example Authentication and Authorization plugin (scala based).
See README.md in the project directory.

### Plugin javaauth

Example Authentication and Authorization plugin (java based).
See README.md in the project directory.
