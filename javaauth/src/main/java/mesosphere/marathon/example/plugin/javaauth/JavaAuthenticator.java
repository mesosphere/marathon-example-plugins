package mesosphere.marathon.example.plugin.javaauth;

import java.util.Base64;
import java.util.concurrent.Executors;

import akka.dispatch.ExecutionContexts;
import akka.dispatch.Futures;
import mesosphere.marathon.plugin.auth.Authenticator;
import mesosphere.marathon.plugin.auth.Identity;
import mesosphere.marathon.plugin.http.HttpRequest;
import mesosphere.marathon.plugin.http.HttpResponse;
import scala.Option;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;

public class JavaAuthenticator implements Authenticator {

    private final ExecutionContext EC = ExecutionContexts.fromExecutorService(Executors.newSingleThreadExecutor());

    @Override
    public Future<Option<Identity>> authenticate(HttpRequest request) {
        return Futures.future(() -> Option.apply(doAuth(request)), EC);
    }

    private Identity doAuth(HttpRequest request) {
        try {
            Option<String> header = request.header("Authorization").headOption();
            if (header.isDefined() && header.get().startsWith("Basic ")) {
                String encoded = header.get().replaceFirst("Basic ", "");
                String decoded = new String(Base64.getDecoder().decode(encoded), "UTF-8");
                String[] userPass = decoded.split(":", 2);
                if (userPass.length == 2) {
                    return doAuth(userPass[0], userPass[1]);
                }
            }
        } catch (Exception ex) { /* do not authenticate in case of exception */ }
        return null;
    }

    /**
     * Authenticate, if the username matches the password.
     */
    private Identity doAuth(String username, String password) {
        if (username.equals(password)) {
            return new JavaIdentity(username);
        } else {
            return null;
        }
    }


    @Override
    public void handleNotAuthenticated(HttpRequest request, HttpResponse response) {
        response.status(401);
        response.header("WWW-Authenticate", "Basic realm=\"Marathon: Username==Password\"");
        response.body("application/json", "{\"problem\": \"Not Authenticated!\"}".getBytes());
    }
}
