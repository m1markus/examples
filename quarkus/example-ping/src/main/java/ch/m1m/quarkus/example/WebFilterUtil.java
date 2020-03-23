package ch.m1m.quarkus.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class WebFilterUtil {

    private static final Logger log = LoggerFactory.getLogger(WebFilterUtil.class);

    public static AsyncContext getOrCreateAsyncContext(ServletRequest request, ServletResponse response) {
        AsyncContext context = null;
        if (request.isAsyncStarted()) {
            log.info("calling request.getAsyncContext...");
            context = request.getAsyncContext();
        } else {
            log.info("calling request.startAsync...");
            context = request.startAsync(request, response);
        }
        return context;
    }
}
