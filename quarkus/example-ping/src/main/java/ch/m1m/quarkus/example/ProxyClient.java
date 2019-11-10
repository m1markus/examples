package ch.m1m.quarkus.example;

import com.google.common.io.ByteStreams;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProxyClient {

    private static final Logger log = LoggerFactory.getLogger(ProxyServlet.class);

    private static final OkHttpClient httpClient = new OkHttpClient();

    public static void forward(ProxyRuleEntry inTarget, HttpServletRequest inRequest, HttpServletResponse inResponse) {

        try (ServletInputStream sis = inRequest.getInputStream()) {

            log.info("req.InputStream={}", sis);
            RequestBody body = null;

            // read the caller request body
            //
            // guava library
            byte[] bytes = ByteStreams.toByteArray(sis);
            if (bytes != null) {
                log.info("req.Body length={}", bytes.length);
                if (bytes.length > 0) {
                    String contentType = inRequest.getHeader("Content-Type");
                    log.info("set body to type: {}", contentType);
                    body = RequestBody.create(bytes, MediaType.parse(contentType));
                }
            }
            log.info("ServletInputStream.isFinished={}", sis.isFinished());

            // create a client
            //
            Request targetRequest = buildTargetRequest(body, inTarget, inRequest);

            try (Response response = httpClient.newCall(targetRequest).execute()) {

                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                // set http status code
                //
                int reponseStatus = response.code();
                log.info("response status code: {}", reponseStatus);
                inResponse.setStatus(reponseStatus);

                // set content-type
                //
                String headerContentType = "content-type";
                String contentTypeValue = response.header(headerContentType);
                log.info("response content-type: {}", contentTypeValue);
                inResponse.setHeader(headerContentType, contentTypeValue);

                // Get response body
                //
                String respBody = response.body().string();
                log.info("target response body: {}", respBody);

                // write response for the caller
                //
                //inResponse.getOutputStream()
                inResponse.getWriter().print(respBody);
            }

        } catch (IOException e) {

            log.error("ProxyClient#forward() IOException ", e);

            inResponse.setStatus(500);
            try {
                inResponse.getWriter().print(e.getMessage());

            } catch (IOException ignore) {
                // ignore
            }
        }
    }

    private static Request buildTargetRequest(RequestBody inBody, ProxyRuleEntry inTarget, HttpServletRequest inRequest) {

        if (inTarget == null) {
            String errorMsg = String.format("no proxy target found for PathInfo=%s", inRequest.getPathInfo());
            log.warn(errorMsg);
            throw new RuntimeException(errorMsg);
        }

        String targetUrl = inTarget.getTargetURL();
        String queryString = inRequest.getQueryString();
        if (queryString != null && !queryString.isEmpty()) {
            targetUrl += "?" + queryString;
        }

        log.info("targetURL={}", targetUrl);

        Request.Builder builder = new Request.Builder().url(targetUrl);
        builder.addHeader("User-Agent", "proxy OkHttp3 Client v4.x.x");

        String method = inRequest.getMethod();

        if ("GET".equals(method)) {

        } else if ("POST".equals(method)) {

            /*
            RequestBody body = new FormBody.Builder()
                    .add("username", "abc")
                    .build();
            */

            if (inBody != null) {
                builder.post(inBody);
            } else {
                log.warn("executing POST without body");
            }

        } else {
            String errorMsg = String.format("proxy method %s not implemented", method);
            log.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        return builder.build();
    }

}
