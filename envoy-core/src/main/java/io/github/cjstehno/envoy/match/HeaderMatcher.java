package io.github.cjstehno.envoy.match;


import io.github.cjstehno.envoy.cfg.Headers;
import io.github.cjstehno.envoy.cfg.OutboundRequest;
import io.github.cjstehno.envoy.impl.match.MapKeyMatcher;
import io.github.cjstehno.envoy.impl.match.MappedValuesMatcher;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static io.github.cjstehno.envoy.cfg.Headers.CONTENT_TYPE;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.core.IsIterableContaining.hasItem;

/**
 * Matcher used to match a request header.
 */
public abstract class HeaderMatcher extends BaseMatcher<OutboundRequest> {

    /**
     * Creates a matcher for a request header that matches the given name matcher and value matcher.
     *
     * @param nameMatcher  the name matcher
     * @param valueMatcher the value matcher
     * @return the header matcher
     */
    public static HeaderMatcher headerMatching(final Matcher<String> nameMatcher, final Matcher<Iterable<? super String>> valueMatcher) {
        return new HeaderMatches(nameMatcher, valueMatcher);
    }

    /**
     * Creates a matcher for a request header that matches the given name and provided value matcher.
     *
     * @param name         the name
     * @param valueMatcher the value matcher
     * @return the header matcher
     */
    public static HeaderMatcher headerMatching(final String name, final Matcher<Iterable<? super String>> valueMatcher) {
        return headerMatching(equalToIgnoringCase(name), valueMatcher);
    }

    /**
     * Creates a matcher for a request header that matches the given name and value.
     *
     * @param name  the name
     * @param value the value
     * @return the header matcher
     */
    public static HeaderMatcher headerMatching(final String name, final String value) {
        return headerMatching(name, hasItem(value));
    }

    /**
     * Creates a matcher that matches when a header exists with the given name.
     *
     * @param name the name
     * @return the header matcher
     */
    public static HeaderMatcher headerExists(final String name) {
        return headerExists(equalToIgnoringCase(name));
    }

    /**
     * Creates a matcher that matches when a header exists matching the given name matcher.
     *
     * @param nameMatcher the name matcher
     * @return the header matcher
     */
    public static HeaderMatcher headerExists(final Matcher<String> nameMatcher) {
        return new HasHeaderMatching(nameMatcher, false);
    }

    /**
     * Creates a matcher that matches when a header does not exist with the given name.
     *
     * @param name the name
     * @return the header matcher
     */
    public static HeaderMatcher headerDoesNotExist(final String name) {
        return headerDoesNotExist(equalToIgnoringCase(name));
    }

    /**
     * Creates a matcher that matches when a header does not exist matching the given name matcher.
     *
     * @param nameMatcher the name matcher
     * @return the header matcher
     */
    public static HeaderMatcher headerDoesNotExist(final Matcher<String> nameMatcher) {
        return new HasHeaderMatching(nameMatcher, true);
    }

    /**
     * Creates a matcher that matches a Content-Type header with the specified value.
     *
     * @param contentType the content type value
     * @return the header matcher
     */
    public static HeaderMatcher contentTypeHeader(final String contentType) {
        return contentTypeHeader(equalTo(contentType));
    }

    /**
     * Creates a matcher that matches a Content-Type header matching the provided matcher.
     *
     * @param contentTypeMatcher the content type value matcher
     * @return the header matcher
     */
    public static HeaderMatcher contentTypeHeader(final Matcher<String> contentTypeMatcher) {
        return headerMatching(CONTENT_TYPE, hasItem(contentTypeMatcher));
    }

    private static class HeaderMatches extends HeaderMatcher {

        private final MappedValuesMatcher matcherDelegate;

        private HeaderMatches(final Matcher<String> nameMatcher, final Matcher<Iterable<? super String>> valueMatcher) {
            matcherDelegate = new MappedValuesMatcher(
                "Request header", nameMatcher, valueMatcher, OutboundRequest::getHeaders
            );
        }

        @Override public boolean matches(final Object actual) {
            return matcherDelegate.matches(actual);
        }

        @Override public void describeTo(final Description description) {
            matcherDelegate.describeTo(description);
        }
    }

    private static class HasHeaderMatching extends HeaderMatcher {

        private final MapKeyMatcher matcherDelegate;

        private HasHeaderMatching(final Matcher<String> nameMatcher, final boolean negated) {
            matcherDelegate = new MapKeyMatcher("Header", nameMatcher, negated, OutboundRequest::getHeaders);
        }

        @Override public boolean matches(final Object actual) {
            return matcherDelegate.matches(actual);
        }

        @Override public void describeTo(final Description description) {
            matcherDelegate.describeTo(description);
        }
    }
}
