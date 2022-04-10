package io.github.cjstehno.envoy.cfg;

import java.util.Deque;
import java.util.Map;

public interface IncomingResponse<T> {

    Map<String, Deque<String>> getHeaders();

    T getContent();
}
