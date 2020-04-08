package ch.m1m.ldap;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;


public class WireMockExample {

    public static void main(String... args) throws IOException {

        int wireMockPort = 7171;

        // start a server
        WireMockServer wireMockServer = new WireMockServer(options().port(wireMockPort));
        wireMockServer.start();

        // connect a mock client
        WireMock wireMock1 = new WireMock("localhost", wireMockPort);

        // register a URL response
        //
        wireMock1.register(wireMockServer.stubFor(get(urlEqualTo("/some/thing"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "text/plain")
                                .withBody("Hello world"))));

        try {

            // run a test client against the wiremock server
            //
            OkHttpClient client = new OkHttpClient.Builder()
                    //.addInterceptor(new HttpLoggingInterceptor())
                    //.cache(new Cache(cacheDir, cacheSize))
                    .connectTimeout(1 * 1000 * 5, TimeUnit.MILLISECONDS)
                    .writeTimeout(1 * 1000 * 5, TimeUnit.MILLISECONDS)
                    .readTimeout(1 * 1000 * 3, TimeUnit.MILLISECONDS)
                    .build();

            String url = "http://localhost:" + 7171 + "/some/thing";

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            String reqBody = response.body().string();

            System.out.format("RESPONSE is: %s", reqBody);

        } catch (SocketTimeoutException ex) {

            throw ex;

        } finally {

            wireMockServer.stop();
        }
    }

}
