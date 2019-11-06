package ch.m1m.quarkus.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class StressPluginEcho implements StressPlugin {

    private static final Logger log = LoggerFactory.getLogger(StressPluginEcho.class);

    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    //private static final String targetUrl = "http://localhost:8080/proxy/api/v1/echo";
    private static final String targetUrl = "http://localhost:8080/api/v1/echo";


    public StressPluginEcho() {
        log.info("set targetUrl={}", targetUrl);
    }

    @Override
    public int execute() {

        int rc = 1;

        UUID uuid = UUID.randomUUID();
        String callerId = uuid.toString();

        log.info("StressPluginEcho running...");

        EchoBody echoBody = new EchoBody();
        echoBody.setCallerId(callerId);
        echoBody.setMessageText("Echo Stress Plugin Client");

        //Duration sleepDuration = Duration.parse("PT0.001S");
        //echoBody.setSleepDuration(sleepDuration.toString());

        try {
            String bodyString = objectMapper.writeValueAsString(echoBody);

            Request.Builder builder = new Request.Builder().url(targetUrl);
            builder.addHeader("User-Agent", "StressPluginEcho Client");

            RequestBody body = RequestBody.create(bodyString, MediaType.parse("application/json"));
            builder.post(body);
            Request echoRequest = builder.build();

            Instant instantStart = Instant.now();
            Response response = httpClient.newCall(echoRequest).execute();
            Instant instantEnd = Instant.now();

            ResponseBody responseBody = response.body();
            String responseBodyJsonString = responseBody.string();
            EchoBody echoedBody = objectMapper.readValue(responseBodyJsonString, EchoBody.class);

            // compare
            //
            if (callerId.equals(echoedBody.getCallerId())) {

                Duration duration = Duration.between(instantStart, instantEnd);
                long milliseconds = duration.toMillis();

                // success
                //
                log.info("received matching callerId={} after milliseconds={}", callerId, milliseconds);
                rc = 0;

            } else {
                log.error("callerId missmatch sent={} received={}", callerId, echoedBody.getCallerId());
                rc = 2;
            }

        } catch (JsonProcessingException e) {
            rc = 3;
            log.error("JsonProcessingException error", e);
        } catch (IOException e) {
            rc = 4;
            log.error("IOException error", e);
        }

        return rc;
    }

}
