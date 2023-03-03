package io.github.cjstehno.envoy.http;

import io.github.cjstehno.envoy.cfg.IncomingResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

@RequiredArgsConstructor
public class WrappedIncomingResponse implements IncomingResponse {

    private final HttpServletResponse response;

    @Override public Map<String, Deque<String>> getHeaders() {
        val headers = new LinkedHashMap<String, Deque<String>>();

        for (val headerName : response.getHeaderNames()) {
            headers.put(headerName, new LinkedList<>(response.getHeaders(headerName)));
        }

        return headers;
    }

    @Override public InputStream getContent() {
        return response.getOutputStream();
    }

}
