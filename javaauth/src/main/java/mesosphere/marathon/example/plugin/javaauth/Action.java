package mesosphere.marathon.example.plugin.javaauth;

import mesosphere.marathon.plugin.auth.*;

/**
 * Enumeration for handling AuthorizedActions more easily in Java.
 */
public enum Action {

    CreateAppOrGroup(CreateGroup$.MODULE$),
    UpdateAppOrGroup(UpdateGroup$.MODULE$),
    DeleteAppOrGroup(DeleteGroup$.MODULE$),
    ViewAppOrGroup(ViewGroup$.MODULE$),
    KillTask(DeleteGroup$.MODULE$);

    public static Action byAction(AuthorizedAction<?> action) {
        for (Action a : values()) {
            if (a.action.equals(action)) {
                return a;
            }
        }
        throw new IllegalArgumentException("Unknown Action: " + action);
    }

    private final AuthorizedAction<?> action;
    Action(AuthorizedAction<?> action) {
        this.action = action;
    }
}
