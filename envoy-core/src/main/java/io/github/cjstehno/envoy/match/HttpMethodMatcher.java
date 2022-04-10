package io.github.cjstehno.envoy.match;

import io.github.cjstehno.envoy.cfg.HttpMethod;
import io.github.cjstehno.envoy.cfg.OutboundRequest;
import lombok.RequiredArgsConstructor;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.AnyOf;

import java.util.Arrays;

import static io.github.cjstehno.envoy.cfg.HttpMethod.ANY;
import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * A matcher used to match the HTTP method of a request.
 */
@RequiredArgsConstructor(access = PRIVATE)
public class HttpMethodMatcher extends BaseMatcher<OutboundRequest> {

    private final Matcher<HttpMethod> matcher;

    /**
     * Creates a matcher to match any o the specified request methods.
     *
     * @param methods the request methods allowed for a match
     * @return the method matcher
     */
    @SuppressWarnings("unchecked")
    public static HttpMethodMatcher methodMatching(final HttpMethod... methods) {
        return new HttpMethodMatcher(
            AnyOf.anyOf(
                Arrays.stream(methods)
                    .map(m -> m == ANY ? any(HttpMethod.class) : equalTo(m))
                    .toList()
                    .toArray(new Matcher[0])
            )
        );
    }

    @Override public boolean matches(final Object actual) {
        return matcher.matches(((OutboundRequest) actual).getMethod());
    }

    @Override public void describeTo(final Description description) {
        description.appendText("HTTP method is ");
        matcher.describeTo(description);
    }
}