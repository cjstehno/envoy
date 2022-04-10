package io.github.cjstehno.envoy.impl;

import io.github.cjstehno.envoy.cfg.IncomingResponse;
import lombok.Getter;

import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public class IncomingResponseImpl<T> implements IncomingResponse<T> {

    private final Map<String, Deque<String>> headers = new LinkedHashMap<>();
    @Getter private final T content;

    public IncomingResponseImpl(final Map<String, Deque<String>> headers, final T content) {
        this.headers.putAll(headers);
        this.content = content;
    }

    @Override public Map<String, Deque<String>> getHeaders() {
        return unmodifiableMap(headers);
    }
}
