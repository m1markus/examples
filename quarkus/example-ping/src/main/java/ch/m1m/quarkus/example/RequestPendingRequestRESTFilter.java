package ch.m1m.quarkus.example;

import io.vertx.core.http.HttpServerRequest;
import org.eclipse.microprofile.metrics.ConcurrentGauge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

@Provider
public class RequestPendingRequestRESTFilter implements ContainerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestPendingRequestRESTFilter.class);

    @Inject
    ApplicationMetrics applicationMetrics;

//    @Context
//    HttpServerRequest request;

    @Override
    public void filter(ContainerRequestContext ctxRequest) {

        final String method = ctxRequest.getMethod();
        final String path = ctxRequest.getUriInfo().getPath();

        //final String address = request.remoteAddress().toString();
        String address = null;

        applicationMetrics.requestStart();

        ConcurrentGauge pendingRequestsGauge = applicationMetrics.getPendingRequestsGauge(path);
        pendingRequestsGauge.inc();
        long currentReqPending = pendingRequestsGauge.getCount();

        log.info("RequestPendingRequestRESTFilter#filter() {} Request on {} from IP {} currentPending {}", method, path, address, currentReqPending);
    }
}
