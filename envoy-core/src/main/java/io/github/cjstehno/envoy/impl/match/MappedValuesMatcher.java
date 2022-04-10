package io.github.cjstehno.envoy.impl.match;

import io.github.cjstehno.envoy.cfg.OutboundRequest;
import lombok.RequiredArgsConstructor;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.function.Function;

import static java.util.Arrays.asList;

/**
 * A matcher used to match a map with String keys mapped to a Deque of Strings. This is a common pattern for various
 * data elements in a request.
 */
@RequiredArgsConstructor
public class MappedValuesMatcher extends BaseMatcher<OutboundRequest> {

    private final String label;
    private final Matcher<String> nameMatcher;
    private final Matcher<Iterable<? super String>> valuesMatcher;
    private final Function<OutboundRequest, Map<String, Deque<String>>> mapProvider;

    @Override public boolean matches(final Object actual) {
        return mapProvider.apply(((OutboundRequest) actual)).entrySet().stream()
            .filter(ent -> nameMatcher.matches(ent.getKey()))
            .anyMatch(ent -> valuesMatcher.matches(new ArrayDeque<>(asList(ent.getValue().toArray(new String[0])))));
    }

    @Override public void describeTo(final Description description) {
        description.appendText(label + " name is ");
        nameMatcher.describeTo(description);
        description.appendText(" and values are ");
        valuesMatcher.describeTo(description);
    }
}
