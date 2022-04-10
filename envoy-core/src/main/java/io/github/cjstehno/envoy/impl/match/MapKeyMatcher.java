package io.github.cjstehno.envoy.impl.match;

import io.github.cjstehno.envoy.cfg.OutboundRequest;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Map;
import java.util.function.Function;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsMapContaining.hasKey;

/**
 * Matcher for matching the existence of a key in a map.
 */
public class MapKeyMatcher extends BaseMatcher<OutboundRequest> {

    private final String label;
    private final Matcher<?> keyMatcher;
    private final Function<OutboundRequest, Map<String, ?>> mapProvider;

    /**
     * Creates a map key matcher.
     *
     * @param label       the description prefix (label)
     * @param nameMatcher the key name matcher
     * @param negated     whether the match is negated
     * @param mapProvider the provider of the data map
     */
    public MapKeyMatcher(final String label, final Matcher<String> nameMatcher, final boolean negated, final Function<OutboundRequest, Map<String, ?>> mapProvider) {
        this.label = label;
        this.keyMatcher = negated ? not(hasKey(nameMatcher)) : hasKey(nameMatcher);
        this.mapProvider = mapProvider;
    }

    @Override public boolean matches(final Object actual) {
        return keyMatcher.matches(mapProvider.apply((OutboundRequest) actual));
    }

    @Override public void describeTo(Description description) {
        description.appendText(label + " name is ");
        keyMatcher.describeTo(description);
    }
}

