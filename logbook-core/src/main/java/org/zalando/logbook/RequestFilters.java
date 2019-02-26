package org.zalando.logbook;

import org.apiguardian.api.API;

import javax.annotation.Nullable;
import java.util.List;

import static org.apiguardian.api.API.Status.MAINTAINED;
import static org.apiguardian.api.API.Status.STABLE;
import static org.zalando.logbook.DefaultFilters.defaultValues;

@API(status = STABLE)
public final class RequestFilters {

    private RequestFilters() {

    }

    @API(status = MAINTAINED)
    public static RequestFilter defaultValue() {
        final List<RequestFilter> defaults = defaultValues(RequestFilter.Default.class);
        return defaults.stream()
                .reduce(replaceBody(BodyReplacers.defaultValue()), RequestFilter::merge);
    }

    public static RequestFilter replaceBody(final BodyReplacer<HttpRequest> replacer) {
        return request -> {
            @Nullable final String replacement = replacer.replace(request);
            return replacement == null ?
                    request :
                    new BodyReplacementHttpRequest(request, replacement);
        };
    }

}
