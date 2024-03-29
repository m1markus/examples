package ch.m1m.quarkus.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

// We just say 'asyncSupported' but this is fake news
//
//@WebFilter(urlPatterns = "/*", asyncSupported = true)
public class RequestPendingWebFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(RequestPendingWebFilter.class);

    @Inject
    ApplicationMetrics applicationMetrics;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("RequestPendingWebFilter RequestPendingWebFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String pathInfo = null;

        try {
            HttpServletRequest httpReq = (HttpServletRequest) request;
            boolean isAsyncSupported = request.isAsyncSupported();
            boolean isAsyncStarted = request.isAsyncStarted();

            //pathInfo = getPathFromRequestURL(httpReq.getRequestURL().toString());
            pathInfo = httpReq.getRequestURI();
            String remoteAddr = httpReq.getRemoteAddr();
            String method = httpReq.getMethod();
            //pathInfo = pathInfo.replaceAll("/", "-");
            log.info("RequestPendingWebFilter doFilter() onRequest {} for {} from {} asyncSupported {} asyncStarted {}",
                    method, pathInfo, remoteAddr, isAsyncSupported, isAsyncStarted);
            applicationMetrics.getPendingRequestsGauge(pathInfo).inc();
            chain.doFilter(request, response);

        } finally {
            log.info("RequestPendingWebFilter doFilter() onResponse");
            applicationMetrics.getPendingRequestsGauge(pathInfo).dec();
        }
    }

    @Override
    public void destroy() {
        log.info("RequestPendingWebFilter RequestPendingWebFilter destroyed");
    }

    private String getPathFromRequestURL(String inURL) {
        String pathFromUrl = "null";
        if (inURL != null) {
            try {
                URL url = new URL(inURL);
                pathFromUrl = url.getPath();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return pathFromUrl;
    }
}