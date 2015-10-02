package mesosphere.marathon.example.plugin.javaauth;

import mesosphere.marathon.plugin.auth.AuthorizedAction;

/**
 * Enumeration for handling AuthorizedActions more easily in Java.
 */
public enum Action {

    CreateAppOrGroup(mesosphere.marathon.plugin.auth.CreateAppOrGroup$.MODULE$),
    UpdateAppOrGroup(mesosphere.marathon.plugin.auth.UpdateAppOrGroup$.MODULE$),
    DeleteAppOrGroup(mesosphere.marathon.plugin.auth.DeleteAppOrGroup$.MODULE$),
    ViewAppOrGroup(mesosphere.marathon.plugin.auth.ViewAppOrGroup$.MODULE$),
    KillTask(mesosphere.marathon.plugin.auth.KillTask$.MODULE$);

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
