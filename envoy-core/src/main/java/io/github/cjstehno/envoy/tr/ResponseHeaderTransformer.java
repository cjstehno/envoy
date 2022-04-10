package io.github.cjstehno.envoy.tr;

import io.github.cjstehno.envoy.cfg.IncomingResponse;
import io.github.cjstehno.envoy.cfg.OutboundRequest;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

@RequiredArgsConstructor(staticName = "update")
public class ResponseHeaderTransformer implements BiFunction<OutboundRequest, IncomingResponse, IncomingResponse> {

    private final String name;
    private final String value;

    @Override public IncomingResponse apply(OutboundRequest request, IncomingResponse incomingResponse) {
        return null;
    }
}
