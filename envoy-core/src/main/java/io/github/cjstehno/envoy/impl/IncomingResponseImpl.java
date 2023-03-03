package io.github.cjstehno.envoy.impl;

import io.github.cjstehno.envoy.cfg.IncomingResponse;
import lombok.Getter;

import java.io.InputStream;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public class IncomingResponseImpl implements IncomingResponse {

    private final Map<String, Deque<String>> headers = new LinkedHashMap<>();
    @Getter private final InputStream content;

    public IncomingResponseImpl(final Map<String, Deque<String>> headers, final InputStream content) {
        this.headers.putAll(headers);
        this.content = content;
    }

    @Override public Map<String, Deque<String>> getHeaders() {
        return unmodifiableMap(headers);
    }
}
