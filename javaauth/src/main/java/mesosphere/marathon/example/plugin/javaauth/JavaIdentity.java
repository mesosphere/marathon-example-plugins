package mesosphere.marathon.example.plugin.javaauth;


import mesosphere.marathon.plugin.auth.Identity;

class JavaIdentity implements Identity {

    private final String name;

    public JavaIdentity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
