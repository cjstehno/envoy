package io.github.cjstehno.envoy.client.jdk;

import io.github.cjstehno.envoy.cfg.IncomingResponse;
import io.github.cjstehno.envoy.cfg.OutboundRequest;
import io.github.cjstehno.envoy.client.EnvoyClient;
import io.github.cjstehno.envoy.client.EnvoyClientException;
import io.github.cjstehno.envoy.impl.IncomingResponseImpl;
import lombok.val;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpResponse.BodyHandlers.ofByteArray;

// FIXME: document
// FIXME: this will be moved into a sub-project once things are stable
//        -or- should I just leave it here as a default client for simplicity?
public class JdkEnvoyClient implements EnvoyClient {
    // TODO: make an abstract base client?

    private final HttpClient http;

    public JdkEnvoyClient() {
        this(newHttpClient());
    }

    public JdkEnvoyClient(final HttpClient httpClient) {
        this.http = httpClient;
    }

    @Override public IncomingResponse send(final OutboundRequest request) throws EnvoyClientException {
        // FIXME: implement others
        // FIXME: how to handle/route exceptions
        return switch (request.getMethod()) {
            case GET -> sendGet(request);
//            case HEAD -> ;
//            case DELETE -> ;
//            case POST -> ;
//            case PUT -> ;
            default -> throw new IllegalStateException("Unexpected method: " + request.getMethod());
        };
    }

    private IncomingResponse sendGet(final OutboundRequest request) throws EnvoyClientException {
        try {
            val httpRequest = HttpRequest.newBuilder(request.getUri());

            // copy over the headers
            request.getHeaders().forEach((name, value) -> {
                httpRequest.header(name, String.join(";", value));
            });

            // TODO: is byte[] the best choice? maybe proviode a content strategy configuration (bytes, file, etc)
            val httpResponse = http.send(httpRequest.build(), ofByteArray());

            val headers = new HashMap<String, Deque<String>>();
            for (val entry : httpResponse.headers().map().entrySet()) {
                headers.put(entry.getKey(), new LinkedList<>(entry.getValue()));
            }

            return new IncomingResponseImpl<>(headers, httpResponse.body());

        } catch (Exception ex) {
            /// FIXME: populate
            throw new EnvoyClientException();
        }
    }
}
