package io.github.cjstehno.envoy.http;

import io.github.cjstehno.envoy.cfg.IncomingResponse;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.util.Deque;
import java.util.Map;

@RequiredArgsConstructor
public class WrappedIncomingResponse implements IncomingResponse<byte[]> {

    private final HttpServletResponse response;

    @Override public Map<String, Deque<String>> getHeaders() {

    }

    @Override public Object getContent() {

    }
}
