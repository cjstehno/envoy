package io.github.cjstehno.envoy.tr;

import io.github.cjstehno.envoy.cfg.OutboundRequest;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor(staticName = "forceHttps")
public class RequestSecureTransformer implements Function<OutboundRequest, OutboundRequest> {

    private final boolean https;

    @Override public OutboundRequest apply(OutboundRequest request) {
        // FIXME: transform
        return request;
    }
}
