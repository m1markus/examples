package ch.m1m.quarkus.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

//@Provider
public class RequestPendingRequestRESTFilter implements ContainerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestPendingRequestRESTFilter.class);

    @Inject
    ApplicationMetrics applicationMetrics;

    @Context
    UriInfo info;

/*

2019-11-05 18:24:09,395 ERROR [org.jbo.res.res.i18n] (executor-thread-1) RESTEASY002005: Failed executing GET /api/v1/echo: org.jboss.resteasy.spi.LoggableFailure: RESTEASY003880: Unable to find contextual data of type: io.vertx.core.http.HttpServerRequest
	at org.jboss.resteasy.core.ContextParameterInjector$GenericDelegatingProxy.invoke(ContextParameterInjector.java:120)

 */
//    @Context
//    HttpServerRequest request;

    @Override
    public void filter(ContainerRequestContext context) {

        //applicationMetrics.getPendingRequestsGauge().inc();

        final String method = context.getMethod();
        final String path = info.getPath();
        //final String address = request.remoteAddress().toString();
        String address = null;

        log.info("RequestPendingRequestRESTFilter#filter() Request {} {} from IP {}", method, path, address);
    }
}
