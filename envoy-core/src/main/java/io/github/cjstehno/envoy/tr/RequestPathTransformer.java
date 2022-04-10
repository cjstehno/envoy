package io.github.cjstehno.envoy.tr;

import io.github.cjstehno.envoy.cfg.OutboundRequest;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor(staticName = "updatePath")
public class RequestPathTransformer implements Function<OutboundRequest, OutboundRequest> {

    private final String path;

    public static RequestPathTransformer replace(final String target, final String replacement){
        // FIXME: how t o impl
        return null;
    }

    @Override public OutboundRequest apply(OutboundRequest request) {
        // FIXME: implement
        return null;
    }
}
