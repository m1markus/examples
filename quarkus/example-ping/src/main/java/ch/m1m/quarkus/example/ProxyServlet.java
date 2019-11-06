package ch.m1m.quarkus.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "ProxyServlet", urlPatterns = "/proxy/*")
public class ProxyServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(ProxyServlet.class);

    private static final String VERSION = "v0.0.2";

    public void init(ServletConfig config) {
        log.info("ProxyServlet initialized");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("doDelete() called...");
        processRequest(request, response);
    }

    @Override
    protected void doHead(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("doHead() called...");
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("doGet() called...");
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("doPost() called...");
        processRequest(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("doPut() called...");
        processRequest(request, response);
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("doOptions() called...");
        processRequest(request, response);
    }

    @Override
    protected void doTrace(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("doTrace() called...");
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

        dumpRequestInfo(request);
        ProxyRuleEntry targetRuleEntry = ProxyRuleUtil.getTarget(request.getPathInfo());
        ProxyClient.forward(targetRuleEntry, request, response);
    }

    private void dumpRequestInfo(HttpServletRequest request) throws IOException {

        log.info("req.Method={}", request.getMethod());
        log.info("req.RequestURI={}", request.getRequestURI());
        log.info("req.PathInfo={}", request.getPathInfo());
        log.info("req.QueryString={}", request.getQueryString());

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            log.info("header: {}={}", key, value);
        }
    }

}
