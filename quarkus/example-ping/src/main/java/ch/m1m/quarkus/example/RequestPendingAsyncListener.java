package ch.m1m.quarkus.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import java.io.IOException;

public class RequestPendingAsyncListener implements AsyncListener {

    private static final Logger log = LoggerFactory.getLogger(RequestPendingAsyncListener.class);

    private String pathInfo;

    RequestPendingAsyncListener(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    @Override
    public void onComplete(AsyncEvent asyncEvent) throws IOException {
        log.info("asyncListener onComplete()");
    }

    @Override
    public void onTimeout(AsyncEvent asyncEvent) throws IOException {
        log.info("asyncListener onTimeout()");
    }

    @Override
    public void onError(AsyncEvent asyncEvent) throws IOException {
        log.info("asyncListener onError()");
    }

    @Override
    public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
        log.info("asyncListener onStartAsync()");
    }
}
