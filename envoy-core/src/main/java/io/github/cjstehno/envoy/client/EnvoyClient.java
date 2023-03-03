package io.github.cjstehno.envoy.client;

import io.github.cjstehno.envoy.cfg.IncomingResponse;
import io.github.cjstehno.envoy.cfg.OutboundRequest;

public interface EnvoyClient {

    void exchange(final OutboundRequest request, final IncomingResponse response) throws EnvoyClientException;
}
