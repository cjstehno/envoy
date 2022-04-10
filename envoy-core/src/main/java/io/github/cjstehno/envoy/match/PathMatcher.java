package io.github.cjstehno.envoy.match;

import io.github.cjstehno.envoy.cfg.OutboundRequest;
import lombok.RequiredArgsConstructor;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.any;

/**
 * Matcher used to match the request path.
 */
@RequiredArgsConstructor(staticName = "pathMatching")
public class PathMatcher extends BaseMatcher<OutboundRequest> {

    private final Matcher<String> matcher;

    /**
     * Configures a matcher expecting a request path equal to the provided path. If "*" is used, it will match any
     * String value.
     *
     * @param path the expected path
     * @return the path matcher
     */
    public static PathMatcher pathMatching(final String path) {
        return pathMatching(path.equals("*") ? any(String.class) : equalTo(path));
    }

    /**
     * Configures a matcher that will match any path.
     *
     * @return the path matcher
     */
    public static PathMatcher anyPath() {
        return pathMatching(any(String.class));
    }

    @Override public boolean matches(final Object actual) {
        return matcher.matches(((OutboundRequest) actual).getPath());
    }

    @Override public void describeTo(final Description description) {
        description.appendText("Path matches ");
        matcher.describeTo(description);
    }
}