package io.github.cjstehno.envoy;

import io.github.cjstehno.envoy.cfg.HttpMethod;
import io.github.cjstehno.envoy.cfg.OutboundRequest;
import io.github.cjstehno.envoy.client.jdk.JdkEnvoyClient;
import io.github.cjstehno.envoy.tr.RequestPathTransformer;
import io.github.cjstehno.envoy.tr.ResponseHeaderTransformer;
import io.github.cjstehno.ersatz.ErsatzServer;
import io.github.cjstehno.ersatz.junit.ErsatzServerExtension;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.github.cjstehno.envoy.cfg.HttpMethod.GET;
import static io.github.cjstehno.envoy.tr.RequestPathTransformer.updatePath;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ErsatzServerExtension.class)
class EnvoyTest {

    private ErsatzServer ersatz = new ErsatzServer(cfg-> {
        cfg.https();
    });

    @Test @DisplayName("basic configuration")
    void basicConfiguration(){
        ersatz.expectations(expects -> {
            expects.GET("/other/else", req -> {
                req.secure();
                req.called(1);
                req.responder(res -> {

                });
            });
        });

        val envoy = new Envoy(cfg -> {
            // FIXME: might make more sense just to put the base URL (e.g. https://localhost:1234) instead - remove secure transform
            cfg.client(new JdkEnvoyClient());
            // cfg.destination("https://localhost:1234");
            // cfg.destination("https", "localhost", 1234);
            // move secure to the root rather than route level
            cfg.destination("localhost", ersatz.getHttpsPort());
            cfg.route(
                // matches().secure().method(GET).path("/something");
                matches -> {
                    matches.method(GET);
                    matches.secure(false);
                    matches.path("/something");
                },
                reqt -> {
                    reqt.secure(true);
                    reqt.path(updatePath("/other/else"));
                },
                rest -> {
                    rest.header(ResponseHeaderTransformer.removeResponseHeader("Transfer-Encoding"));
                }
            );
        });

        // http://<host>:<port>/something --> https://<host>:<port>/other/else (no transfer-encoding)

         envoy.handle(request, response);

        // FIXME: check response

        assertTrue(ersatz.verify());
    }
}