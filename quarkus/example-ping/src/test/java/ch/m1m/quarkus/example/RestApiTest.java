package ch.m1m.quarkus.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class RestApiTest {

    private static final String API_URL_PREFIX = "/api/v1/junit";

    @Test
    public void testPingEndpoint() {
        given()
          .when().get(API_URL_PREFIX + "/ping")
          .then()
             .statusCode(200)
             .body(containsString("pong"));
    }

    @Test
    public void testProxyPingEndpoint() {
        given()
            .when().get("/proxy" + API_URL_PREFIX + "/ping")
            .then()
                .statusCode(200)
                .body(containsString("pong"));
    }

}