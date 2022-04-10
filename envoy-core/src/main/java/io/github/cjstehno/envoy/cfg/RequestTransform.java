package io.github.cjstehno.envoy.cfg;

import io.github.cjstehno.envoy.tr.*;

import java.util.function.Function;

/**
 * FIXME: document
 * <p>
 * used to transform the outgoing request
 */
public interface RequestTransform {

    // will make the outbound request http or https
    default RequestTransform secure(final boolean https) {
        return secure(RequestSecureTransformer.forceHttps(https));
    }

    default RequestTransform secure(final RequestSecureTransformer tr) {
        return transformer(tr);
    }

    // will change the outbound request method to
    default RequestTransform method(final HttpMethod method) {
        return method(RequestMethodTransformer.force(method));
    }

    default RequestTransform method(final RequestMethodTransformer tr) {
        return transformer(tr);
    }

    default RequestTransform path(final String path) {
        return path(RequestPathTransformer.updatePath(path));
    }

    default RequestTransform path(final RequestPathTransformer tr) {
        return transformer(tr);
    }

    default RequestTransform header(final String name, final String value) {
        return header(RequestHeaderTransformer.update(name, value));
    }

    default RequestTransform header(final RequestHeaderTransformer tr) {
        return transformer(tr);
    }

    default RequestTransform query(final String name, final String value) {
        return query(RequestQueryParamTransformer.update(name, value));
    }

    default RequestTransform query(final RequestQueryParamTransformer tr) {
        return transformer(tr);
    }

    RequestTransform transformer(final Function<OutboundRequest, OutboundRequest> tr);
}
