package io.github.cjstehno.envoy.impl;

import io.github.cjstehno.envoy.cfg.*;
import io.github.cjstehno.envoy.client.EnvoyClient;
import io.github.cjstehno.envoy.client.jdk.JdkEnvoyClient;
import lombok.Getter;
import lombok.val;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static io.github.cjstehno.envoy.cfg.UnmatchedBehavior.PROXY;

public class EnvoyConfigImpl implements EnvoyConfig {

    private final RequestMatcherImpl globalMatcher = new RequestMatcherImpl();
    private final List<RouteConfig> routes = new LinkedList<>();
    @Getter private EnvoyClient client;
    private String destinationHost = "localhost";
    private int destinationPort = 80;
    @Getter private UnmatchedBehavior unmatchedBehavior = PROXY;

    @Override public EnvoyConfig client(final EnvoyClient client) {
        this.client = client;
        return this;
    }

    @Override public EnvoyConfig destinationHost(final String host) {
        destinationHost = host;
        return this;
    }

    @Override public EnvoyConfig destinationPort(final int port) {
        destinationPort = port;
        return this;
    }

    @Override public EnvoyConfig unmatchedBehavior(final UnmatchedBehavior behavior) {
        unmatchedBehavior = behavior;
        return this;
    }

    @Override public EnvoyConfig requestsMatching(final Consumer<RequestMatcher> matches) {
        matches.accept(globalMatcher);
        return this;
    }

    @Override
    public RouteConfig route(
        final Consumer<RequestMatcher> matches,
        final Consumer<RequestTransform> requestTransform,
        final Consumer<ResponseTransform> responseTransform
    ) {
        val route = new RouteConfigImpl(globalMatcher, matches, requestTransform, responseTransform);
        routes.add(route);
        return route;
    }

    // FIXME: multiple matches - returns first in list
    public Optional<RouteConfig> findMatch(final OutboundRequest request) {
        return routes.stream().filter(r -> ((RouteConfigImpl) r).matches(request)).findAny();
    }
}
