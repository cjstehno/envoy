package io.github.cjstehno.envoy.impl;

import io.github.cjstehno.envoy.cfg.*;

import java.util.function.Consumer;

public class RouteConfigImpl implements RouteConfig {

    private final RequestMatcherImpl requestMatcher;

    public RouteConfigImpl(final RequestMatcherImpl globalMatcher, final Consumer<RequestMatcher> matcher, final Consumer<RequestTransform> reqTr, final Consumer<ResponseTransform> resTr) {
        requestMatcher = new RequestMatcherImpl(globalMatcher);
        matcher.accept(requestMatcher);

        // FIXME: impl
//        reqTr.accept();

        // FIXME: impl
//        resTr.accept();
    }

    boolean matches(final OutboundRequest request){
        return requestMatcher.matches(request);
    }
}
