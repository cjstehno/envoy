package io.github.cjstehno.envoy.impl;

import io.github.cjstehno.envoy.cfg.OutboundRequest;
import io.github.cjstehno.envoy.cfg.RequestMatcher;
import lombok.val;
import org.hamcrest.Matcher;

import java.util.LinkedList;
import java.util.List;

public class RequestMatcherImpl implements RequestMatcher {

    private final List<Matcher<OutboundRequest>> matchers = new LinkedList<>();
    private final RequestMatcherImpl parentMatcher;

    public RequestMatcherImpl() {
        this(null);
    }

    public RequestMatcherImpl(final RequestMatcherImpl requestMatcher) {
        this.parentMatcher = requestMatcher;
    }

    @Override public RequestMatcher matcher(final Matcher<OutboundRequest> matcher) {
        matchers.add(matcher);
        return this;
    }

    // FIXME:
    public boolean matches(final OutboundRequest request) {
        // first check the parent matchers
        val parentMatches = parentMatcher == null || parentMatcher.matches(request);

        // then check my matchers
        return parentMatches && matchers.stream().allMatch(m -> m.matches(request));
    }
}
