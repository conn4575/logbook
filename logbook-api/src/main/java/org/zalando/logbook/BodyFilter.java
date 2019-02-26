package org.zalando.logbook;

import org.apiguardian.api.API;

import javax.annotation.Nullable;

import static org.apiguardian.api.API.Status.STABLE;

@API(status = STABLE)
@FunctionalInterface
public interface BodyFilter {

    /**
     * Marker interface to signal that a filter should be active by default.
     *
     * @see java.util.ServiceLoader
     */
    interface Default extends BodyFilter {

    }

    String filter(@Nullable final String contentType, final String body);
    
    static BodyFilter none() {
        return (contentType, body) -> body;
    }

    static BodyFilter merge(final BodyFilter left, final BodyFilter right) {
        return (contentType, body) ->
                left.filter(contentType, right.filter(contentType, body));
    }

}
