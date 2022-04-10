package io.github.cjstehno.envoy.cfg;

import io.github.cjstehno.envoy.impl.RequestMatcherImpl;
import io.github.cjstehno.envoy.impl.match.RequestSchemeMatcher;
import io.github.cjstehno.envoy.match.HeaderMatcher;
import io.github.cjstehno.envoy.match.HttpMethodMatcher;
import io.github.cjstehno.envoy.match.PathMatcher;
import io.github.cjstehno.envoy.match.QueryParamMatcher;
import org.hamcrest.Matcher;

import static io.github.cjstehno.envoy.match.HeaderMatcher.headerMatching;
import static io.github.cjstehno.envoy.match.QueryParamMatcher.queryExists;
import static io.github.cjstehno.envoy.match.QueryParamMatcher.queryMatching;

public interface RequestMatcher {

    static RequestMatcher matches() {
        // FIXME: ???
        return new RequestMatcherImpl();
    }

    default RequestMatcher secure() {
        return secure(true);
    }

    default RequestMatcher secure(final boolean value) {
        return matcher(new RequestSchemeMatcher(value));
    }

    default RequestMatcher method(final HttpMethod method) {
        return method(HttpMethodMatcher.methodMatching(method));
    }

    default RequestMatcher method(final HttpMethodMatcher matcher) {
        return matcher(matcher);
    }

    default RequestMatcher path(final String value) {
        return path(PathMatcher.pathMatching(value));
    }

    default RequestMatcher path(final Matcher<String> matcher) {
        return path(PathMatcher.pathMatching(matcher));
    }

    default RequestMatcher path(final PathMatcher matcher) {
        return matcher(matcher);
    }

    default RequestMatcher header(final String name, final String value) {
        return header(headerMatching(name, value));
    }

    default RequestMatcher header(final String name, final Matcher<Iterable<? super String>> matcher) {
        return header(headerMatching(name, matcher));
    }

    default RequestMatcher header(final HeaderMatcher headerMatcher) {
        return matcher(headerMatcher);
    }

    default RequestMatcher query(final String name, final String value) {
        return query(queryMatching(name, value));
    }

    default RequestMatcher query(final String name) {
        return query(queryExists(name));
    }

    default RequestMatcher query(final String name, final Iterable<? super String> values) {
        return query(queryMatching(name, values));
    }

    default RequestMatcher query(final String name, final Matcher<Iterable<? super String>> matcher) {
        return query(queryMatching(name, matcher));
    }

    default RequestMatcher query(final QueryParamMatcher queryMatcher) {
        return matcher(queryMatcher);
    }


    RequestMatcher matcher(final Matcher<OutboundRequest> matcher);
}
