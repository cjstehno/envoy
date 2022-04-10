package io.github.cjstehno.envoy.cfg;

/**
 * Enum of possible "unmatched behavior" options.
 */
public enum UnmatchedBehavior {

    /**
     * Denotes that an unmatched request will be passed through as a simple proxy.
     */
    PROXY,

    /**
     * Denotes that an unmatched request will be forwarded as-is.
     */
    FORWARD,

    /**
     * Denotes that an unmatched request will be ignored.
     */
    IGNORE,

    /**
     * Denotes that an unmatched request will result in an error.
     */
    ERROR;

}
