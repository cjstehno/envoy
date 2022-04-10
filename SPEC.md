Envoy
n. an accreditied messener or representative

A simple yet configurable proxy client (not intended for load balancing but for translating requests to another service)


val proxy = new ProxyClient(cfg -> {
cfg.destination("somehost", 1234);


	cfg.route("/some/full/path", );
	cfg.route(matcher, );
});

val proxy = new ProxyClient("destination", 8182);

proxy.route("/some/absolute/path", out -> {
// translation config
});

proxy.route(RouteMatchers.pathStartsWith("/some/), our -> {
// transformation config
});

val proxy = new Envoy(cfg -> {
cfg.client(JdkEnvoyClient.class | OkHttpEnvoyClient | ApacheHttpEnvoyClient) // Jdk is default if not specified
cfg.destination("somehost", 1234); // defaults to localhost 80 if not set
cfg.unmatchedBehavior(FORWARD | TRANSFORM | DROP | ERROR);

	// global matching (like ersatz) - these are applied before the route matchers
	// global request transformations - apply these then the route
	// global response transfomrations - apply these then the route
	
	cfg.route(
		matches -> {
			matches.secure(...);
			matches.method(...); // should I do more like Ersatz?
			matches.path(...);
			matches.header(...);
			matches.query(...);
		},
		reqtx -> {
			// change the request information - should have access to incoming request data
			// these changes should be made right before sending (not as applied)
			reqtx.secure(...);
			reqtx.method(method-transformer);
			reqtx.path(pathtransformer -or- simple-path);
			reqtx.header(...)
			reqtx.query(...)
		},
		restx -> {
			// change the response information - should have access to the incoming request and the response
	
			restx.header(...)
			restx.cookie(...)
		}
	).listen(listener-consumer);
});

Optional<EnvoyResponse> response = proxy.accept(request);

/// whatn happens when not matched?
- thinking that non-matched requests should be forwarded as is or dropped (config)
  PROXY (default?) - perform a simple destination replacement and forward the rquest (simple proxy)
  FORWARD - just execute the request and return the response
  DROP - drop/ignore the request (return empty option)
  ERROR - throw an execition


each instance works for one destination host/port
ability to configure multiple routes with full path and matcher (with support for ersatz level matching)
ability to translate request:
- should be easy to passthrough a request for a simple proxy

	- should be able to change the method of a proxied call
	
	- should be able to match path
	- should be able to match secure or not
	- should be able to add/remove/edit (request) headers, cookies, query string 
	- should be able to change request to/from https
	
	- should be able to add/remove/edit (response) headers, cookies



- different sub-libs for different clients (or simple way to integrate)
  defaults to built-in java client with suport for okhttp and apache


consider ability to define default global request/response transformations

- a listener that will fire when a route is matched (monitoring purposes)

- DOES NOT modify the request or response content


request comes in
check request for match
if MATCHED -> fire listener -> transform rquest -> send request -> transform response -> return response
else if unmatched behavior is:
PROXY -> transform request (default) -> send request -> transform response (Default) -> return response
FORWARD -> send request (as is) -> return response
DROP -> return empty optional
ERROR -> throw exception


Core project is API and default JDK client implemention
sub-lib envoy-okhttp and envoy-apache or separate client impls that require the core and provid an additional client impl