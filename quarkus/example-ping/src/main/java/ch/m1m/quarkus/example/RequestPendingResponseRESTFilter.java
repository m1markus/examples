package ch.m1m.quarkus.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class RequestPendingResponseRESTFilter implements ContainerResponseFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestPendingResponseRESTFilter.class);

    @Inject
    ApplicationMetrics applicationMetrics;

    @Override
    public void filter(ContainerRequestContext ctxRequest, ContainerResponseContext ctxResponse) {

        final String path = ctxRequest.getUriInfo().getPath();
        boolean skipDecrement = false;

        if ("/favicon.ico".equals(path)) {
            skipDecrement = true;
        }

        if (!skipDecrement) {
            log.info("RequestPendingResponseRESTFilter#filter() Response on {}", path);
            applicationMetrics.getPendingRequestsGauge(path).dec();
            applicationMetrics.requestEnd();
        } else {
            log.warn("RequestPendingResponseRESTFilter#filter() Skipped metric decrement on {}", path);
        }
    }
}