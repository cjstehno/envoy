package io.github.cjstehno.envoy.tr;

import io.github.cjstehno.envoy.cfg.HttpMethod;
import io.github.cjstehno.envoy.cfg.OutboundRequest;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;


@RequiredArgsConstructor(staticName = "force")
public class RequestMethodTransformer implements Function<OutboundRequest, OutboundRequest> {

    private final HttpMethod method;

    @Override public OutboundRequest apply(final OutboundRequest request) {

        // FIXME: transform request to have forced method
        return request;
    }
}
