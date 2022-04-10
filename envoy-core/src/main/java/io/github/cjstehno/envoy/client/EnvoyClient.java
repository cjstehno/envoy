package io.github.cjstehno.envoy.client;

import io.github.cjstehno.envoy.cfg.IncomingResponse;
import io.github.cjstehno.envoy.cfg.OutboundRequest;

public interface EnvoyClient {

    IncomingResponse send(final OutboundRequest request) throws EnvoyClientException;
}
