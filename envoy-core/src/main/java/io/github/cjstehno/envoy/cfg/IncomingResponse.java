package io.github.cjstehno.envoy.cfg;

import java.io.InputStream;
import java.util.Deque;
import java.util.Map;

public interface IncomingResponse {

    Map<String, Deque<String>> getHeaders();

    InputStream getContent();
}
