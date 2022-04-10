package io.github.cjstehno.envoy.cfg;

import io.github.cjstehno.envoy.tr.ResponseHeaderTransformer;

import java.util.function.BiFunction;

public interface ResponseTransform {

    default ResponseTransform header(final String name, final String value) {
        return header(ResponseHeaderTransformer.update(name, value));
    }

    default ResponseTransform header(final ResponseHeaderTransformer tr) {
        return transformer(tr);
    }

    ResponseTransform transformer(final BiFunction<OutboundRequest, IncomingResponse, IncomingResponse> tr);
}
