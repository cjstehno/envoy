package io.github.cjstehno.envoy.cfg;

import io.github.cjstehno.envoy.client.EnvoyClient;

import java.util.function.Consumer;

public interface EnvoyConfig {

    // FIXME: document that this is required
    // FIXME: might be good to error out quickly if not set?
    EnvoyConfig client(final EnvoyClient client);

    default EnvoyConfig destination(final String host, final int port) {
        destinationHost(host);
        destinationPort(port);
        return this;
    }

    /**
     * Used to specify the destination host name (defaults to localhost).
     *
     * @param host the destination host name
     * @return a reference to this config object
     */
    EnvoyConfig destinationHost(final String host);

    /**
     * Used to specify the destination port name (defaults to 80).
     *
     * @param port the destination port
     * @return a reference to this config object
     */
    EnvoyConfig destinationPort(final int port);

    /**
     * Used to specify how an unmatched request will be handled.
     *
     * @param behavior the unmatched request behavior.
     * @return a reference to this config object
     */
    EnvoyConfig unmatchedBehavior(final UnmatchedBehavior behavior);

    // global matchers (all must match) - this should be called no more than once
    EnvoyConfig requestsMatching(final Consumer<RequestMatcher> matches);

    // FIXME: global request transformers
    // EnvoyConfig transformRequests(final Consumer<RequestTransform> tr);

    // FIXME: global response transformers
    // EnvoyConfig transformResponses(final Consumer<ResponseTransform> tr);

    /**
     * FIXME: document
     *
     * @param matches
     * @param requestTransform
     * @param responseTransform
     * @return
     */
    RouteConfig route(final Consumer<RequestMatcher> matches, final Consumer<RequestTransform> requestTransform, final Consumer<ResponseTransform> responseTransform);
}
