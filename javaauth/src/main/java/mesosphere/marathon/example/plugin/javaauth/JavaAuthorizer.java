package mesosphere.marathon.example.plugin.javaauth;

import mesosphere.marathon.plugin.PathId;
import mesosphere.marathon.plugin.auth.AuthorizedAction;
import mesosphere.marathon.plugin.auth.Authorizer;
import mesosphere.marathon.plugin.auth.Identity;
import mesosphere.marathon.plugin.http.HttpRequest;
import mesosphere.marathon.plugin.http.HttpResponse;

public class JavaAuthorizer implements Authorizer {

    @Override
    public <Resource> boolean isAuthorized(Identity principal, AuthorizedAction<Resource> action, Resource resource) {
        return principal instanceof JavaIdentity &&
                resource instanceof PathId &&
                isAuthorized((JavaIdentity) principal, Action.byAction(action), (PathId) resource);

    }

    private boolean isAuthorized(JavaIdentity principal, Action action, PathId path) {
        switch (action) {
            case CreateAppOrGroup:
                return true;
            case UpdateAppOrGroup:
                return principal.getName().contains("ernie");
            case DeleteAppOrGroup:
                return path.toString().startsWith("/test");
            case ViewAppOrGroup:
                return true;
            case KillTask:
                return false;
            default:
                return false;
        }
    }

    @Override
    public void handleNotAuthorized(Identity principal, HttpRequest request, HttpResponse response) {
        response.status(403);
        response.body("application/json", "{\"problem\": \"Not Authorized to perform this action!\"}".getBytes());
    }
}
