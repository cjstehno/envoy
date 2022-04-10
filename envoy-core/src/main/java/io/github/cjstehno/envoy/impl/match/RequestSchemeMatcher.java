package io.github.cjstehno.envoy.impl.match;

import io.github.cjstehno.envoy.cfg.OutboundRequest;
import lombok.RequiredArgsConstructor;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Matcher used to match the request scheme to determine whether it is secure (HTTPS) or not (HTTP).
 */
@RequiredArgsConstructor
public class RequestSchemeMatcher extends BaseMatcher<OutboundRequest> {

    private final boolean secure;

    @Override public boolean matches(final Object actual) {
        return ((OutboundRequest) actual).getScheme().equalsIgnoreCase(secure ? "HTTPS" : "HTTP");
    }

    @Override public void describeTo(Description description) {
        description.appendText("Scheme is " + (secure ? "HTTPS" : "HTTP"));
    }
}
