package io.github.cjstehno.envoy;

import io.github.cjstehno.envoy.cfg.EnvoyConfig;
import io.github.cjstehno.envoy.cfg.IncomingResponse;
import io.github.cjstehno.envoy.cfg.OutboundRequest;
import io.github.cjstehno.envoy.client.EnvoyClientException;
import io.github.cjstehno.envoy.http.WrappedIncomingResponse;
import io.github.cjstehno.envoy.http.WrappedOutgoingRequest;
import io.github.cjstehno.envoy.impl.EnvoyConfigImpl;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.function.Consumer;

/**
 * The entry point for the Envoy Routing client framework.
 */
@Slf4j
public class Envoy {
    // FIXME: logging

    private final EnvoyConfigImpl envoyConfig;

    public Envoy() {
        envoyConfig = new EnvoyConfigImpl();
    }

    public Envoy(final Consumer<EnvoyConfig> config) {
        this();
        if (config != null) {
            config.accept(envoyConfig);
        }
    }

    public void handle(final HttpServletRequest request, final HttpServletResponse response) throws EnvoyClientException {
        handle(new WrappedOutgoingRequest(request), new WrappedIncomingResponse(response));
    }

    public void handle(final OutboundRequest request, final IncomingResponse response) throws EnvoyClientException {
        val route = envoyConfig.findMatch(request);
        if (route.isPresent()) {

            // FIXME: apply the request transformations
            // FIXME: add support for content transofmration

            envoyConfig.getClient().exchange(request, response);

            // FIXME: apply the response transformations
            // FIXME: add support for content transformations

        } else {
            // no match - how should we handle it?
            switch (envoyConfig.getUnmatchedBehavior()) {
                case PROXY -> proxyRequest(request, response);
                case FORWARD -> forwardRequest(request, response);
                case IGNORE -> log.info("Ignored request...");
                case ERROR -> throw new UnmatchedRequestException();
            }
        }
    }

    private void proxyRequest(final OutboundRequest request, final IncomingResponse response) {
        // FIXME: do a simple proxy of the request and return the response
        // FIXME: should global transformations be applied? - I think yes since its a proxy
    }

    private void forwardRequest(final OutboundRequest request, final IncomingResponse response) {
        // FIXME: forward the request as is and return the response
        // FIXME: should global transformations be applied? - I think no since its as-is
    }
}
