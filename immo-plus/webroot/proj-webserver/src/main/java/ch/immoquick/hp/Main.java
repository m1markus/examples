package ch.immoquick.hp;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.time.ZonedDateTime;

public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {

        try {

            System.out.println("starting...");

            log.info("info test only");
            log.error("error test only");

            Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(10));

            vertx.setPeriodic(1000, id -> {
                // This handler will get called every second
                System.out.println("timer fired! " + ZonedDateTime.now().toString());
            });

            vertx.deployVerticle(WebServer.class.getName(), res -> {
                if (res.succeeded()) {

                } else {
                    log.error("failed to start internal web-server", res.cause());
                }
            });

            Thread.sleep(3 * 1000);

            System.out.println("### end ###");
            //System.exit(0);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
