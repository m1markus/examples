package ch.immoquick.hp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class WebServer extends AbstractVerticle {

    private HttpServer server;

    @Override
    public void start(Future<Void> startFuture) {
        server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.route("/my/*").handler(routingContext -> {

            String msg = "handler /my/* #-1911-#";
            System.out.println(msg);

            // This handler will be called for every request
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/plain");

            //response.setStatusCode(200);

            // Write to the response and end it
            response.end(msg);
        });

        router.route().handler(routingContext -> {

            String msg = "in handler 1";
            System.out.println(msg);

            routingContext.next();

            HttpServerResponse response = routingContext.response();
            int statusCode = response.getStatusCode();

            msg = "out handler 1 statusCode " + statusCode;
            System.out.println(msg);
        });

        router.route().handler(routingContext -> {

            String msg = "handler 2";
            System.out.println(msg);

            // This handler will be called for every request
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/plain");

            response.setStatusCode(201);

            // Write to the response and end it
            response.end("Hello World from Vert.x-Web!");
        });

        server.requestHandler(router::accept).listen(8080);
    }

    @Override
    public void stop(Future<Void> stopFuture) {
        server.close(res -> {
            if (res.succeeded()) {
                stopFuture.complete();
            } else {
                stopFuture.fail(res.cause());
            }
        });
    }
}