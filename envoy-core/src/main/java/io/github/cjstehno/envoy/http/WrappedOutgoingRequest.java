package io.github.cjstehno.envoy.http;

import io.github.cjstehno.envoy.cfg.HttpMethod;
import io.github.cjstehno.envoy.cfg.OutboundRequest;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Deque;
import java.util.Map;

@RequiredArgsConstructor
public class WrappedOutgoingRequest implements OutboundRequest {

    private final HttpServletRequest request;


    @Override public HttpMethod getMethod() {
        return null;
    }

    @Override public String getPath() {
        return null;
    }

    @Override public URI getUri() {
        return null;
    }

    @Override public Map<String, Deque<String>> getHeaders() {
        return null;
    }

    @Override public Map<String, Deque<String>> getQueryParams() {
        return null;
    }

    @Override public String getScheme() {
        return null;
    }
}
