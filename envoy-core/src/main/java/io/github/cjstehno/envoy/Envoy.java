package io.github.cjstehno.envoy;

import io.github.cjstehno.envoy.cfg.EnvoyConfig;
import io.github.cjstehno.envoy.cfg.IncomingResponse;
import io.github.cjstehno.envoy.cfg.OutboundRequest;
import io.github.cjstehno.envoy.client.EnvoyClientException;
import io.github.cjstehno.envoy.impl.EnvoyConfigImpl;
import lombok.val;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * The entry point for the Envoy Routing client framework.
 */
public class Envoy {
    // FIXME: logging

    private final EnvoyConfigImpl envoyConfig;

    public Envoy(){
        envoyConfig = new EnvoyConfigImpl();
    }

    public Envoy(final Consumer<EnvoyConfig> config){
        this();
        if( config != null ){
            config.accept(envoyConfig);
        }
    }

    public Optional<HttpServletResponse> accept(final HttpServletRequest request){
        // FIXME: this should wrap the request/response objects and hand off to the other method
        return Optional.empty();
    }

    public Optional<IncomingResponse> accept(final OutboundRequest request) throws EnvoyClientException {
        val route = envoyConfig.findMatch(request);
        if( route.isPresent()){

            // FIXME: apply the request transformations

            val response = envoyConfig.getClient().send(request);

            // FIXME: apply the response transformations

            return Optional.of(response);

        } else {
            // no match - how should we handle it?
            return switch (envoyConfig.getUnmatchedBehavior()){
                case PROXY -> Optional.of(proxyRequest(request));
                case FORWARD -> Optional.of(forwardRequest(request));
                case IGNORE -> Optional.empty();
                case ERROR -> throw new UnmatchedRequestException();
            };
        }
    }

    private IncomingResponse proxyRequest(final OutboundRequest request){
        // FIXME: do a simple proxy of the request and return the response
        // FIXME: should global transformations be applied? - I think yes since its a proxy
        return null;
    }

    private IncomingResponse forwardRequest(final OutboundRequest request){
        // FIXME: forward the request as is and return the response
        // FIXME: should global transformations be applied? - I think no since its as-is
        return null;
    }
}
