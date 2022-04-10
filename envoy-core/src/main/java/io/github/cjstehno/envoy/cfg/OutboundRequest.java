package io.github.cjstehno.envoy.cfg;

import java.net.URI;
import java.util.Deque;
import java.util.Map;

public interface OutboundRequest {

     HttpMethod getMethod();

     String getPath();

     // FIXME: this should have the outgoing base, path, and query string on it
     URI getUri();

     /**
      * Retrieves the request headers.
      *
      * @return the request headers
      */
     Map<String, Deque<String>> getHeaders();

     /**
      * Retrieves the URL query string parameters for the request.
      *
      * @return the query string parameters
      */
     Map<String, Deque<String>> getQueryParams();

     String getScheme();

     // TODO: should have a method to access the original HttpServletRequest
}
