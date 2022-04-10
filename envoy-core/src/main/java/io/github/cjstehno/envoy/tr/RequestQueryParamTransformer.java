package io.github.cjstehno.envoy.tr;

import io.github.cjstehno.envoy.cfg.OutboundRequest;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor(staticName = "update")
public class RequestQueryParamTransformer implements Function<OutboundRequest, OutboundRequest> {

    private final String name;
    private final String value;

    @Override public OutboundRequest apply(OutboundRequest request) {
        return null;
    }
}
