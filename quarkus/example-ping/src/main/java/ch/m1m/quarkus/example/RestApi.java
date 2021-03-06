package ch.m1m.quarkus.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@Path("api/v1")
public class RestApi {

    private static final Logger log = LoggerFactory.getLogger(RestApi.class);

    // JUnit Test and IT methods
    //
    @GET
    @Path("junit/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String pingGetJunit(@QueryParam("greeting") String greeting,
                               @QueryParam("name") String name) {

        String retMsg = String.format("pong greeting=%s name=%s", greeting, name);
        log.info("/ping GET called... returning '{}'", retMsg);

        return retMsg;
    }

    @POST
    @Path("junit/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public Response pingPostJunit() {

        String retBody = "pong\npong\npong";
        return Response.ok(retBody).build();
    }

    // ...ToPlayWith tests
    // http://localhost:8080/proxy/api/v1/ping?greeting=h%20i&name=markus
    //
    @GET
    @Path("ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String pingGet(@QueryParam("greeting") String greeting,
                          @QueryParam("name") String name) {

        String retMsg = String.format("pong greeting=%s name=%s", greeting, name);
        log.info("/ping GET called... returning '{}'", retMsg);

        return retMsg;
    }

    @POST
    @Path("ping")
    @Produces(MediaType.TEXT_PLAIN)
    public Response pingPost() {

        String retBody = "pong\npong\npong";
        return Response.ok(retBody).build();
    }

    @GET
    @Path("echo")
    @Produces(MediaType.APPLICATION_JSON)
    public EchoBody echoGet(@QueryParam("callerid") String callerId,
                            @QueryParam("messagetext") String messageText) {

        EchoBody echo = new EchoBody();
        echo.setCallerId(callerId != null ? callerId : "cId");
        echo.setMessageText(messageText != null ? messageText : "example text");

        UUID uuid = UUID.randomUUID();
        String serverId = uuid.toString();
        echo.setServerId(serverId);

        log.info("/echo GET called... returning serverId={}", serverId);

        return echo;
    }

    // url: http://localhost:8080/api/v1/echo?callerid=567&messagetext=hello%20x
    //
    //    {
    //        "callerId":"567",
    //        "messageText":"hello x",
    //        "sleepDuration": "PT0.345S",
    //        "serverId":"7a038188-c46d-4d12-9912-be2de8fb88f6"
    //    }
    //
    @POST
    @Path("echo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EchoBody echoPost(EchoBody echo) {

        log.info("/echo POST called");

        // set unique serverId
        //
        UUID uuid = UUID.randomUUID();
        String serverId = uuid.toString();
        echo.setServerId(serverId);

        // sleep if asked for a period/duration
        //
        String inSleepDuration = echo.getSleepDuration();
        if (inSleepDuration != null) {
            Duration sleepDuration = Duration.parse(inSleepDuration);
            if (sleepDuration != null) {
                try {
                    log.info("calling sleep sleepDuration={}", sleepDuration);
                    TimeUnit.NANOSECONDS.sleep(sleepDuration.toNanos());
                } catch (InterruptedException e) {
                    log.error("failed to sleep for duration={}", inSleepDuration);
                }
            }
        }

        if (false) {
            log.info("throw RuntimeException");
            throw new RuntimeException("test to see if the pending_request counter works");
        }

        log.info("/echo POST called... returning serverId={} sleepDuration={}", serverId, inSleepDuration);

        return echo;
    }

    // async methods
    //
    @GET
    @Path("async")
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<String> hello() {
        /*
        return CompletableFuture.supplyAsync(() -> {
            return "hello async";
        });
        */
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            log.info("GET async calling sleep 5");
            Thread.sleep(5_000);
            log.info("GET async calling sleep 5 returned");
            completableFuture.complete("Hello async after 5 seconds");
            return null;
        });

        log.info("GET async endpoint returned a future");
        return completableFuture;
    }

    @GET
    @Path("xxx")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello_sync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = (CompletableFuture<String>) hello();
        String ret = future.get();
        return "xxx sync";
    }

    // interface for the 'proxy' service
    //
    @GET
    @Path("proxy")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProxyRuleEntry> getAllProxyEntries() {

        return ProxyRuleUtil.getAll();
    }

    @GET
    @Path("proxy/deleteAll")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteAllProxyEntries() {

        ProxyRuleUtil.deleteAll();
    }

    @POST
    @Path("proxy")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProxyEntriy(ProxyRuleEntry inEntry) {

        int rc = ProxyRuleUtil.addProxyEntry(inEntry);

        return Response.status(rc).build();
    }

    @DELETE
    @Path("proxy")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProxyEntriy(ProxyRuleEntry inEntry) {

        int rc = ProxyRuleUtil.deleteProxyEntry(inEntry);

        return Response.status(rc).build();
    }

    @PUT
    @Path("proxy")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProxyEntriy(ProxyRuleEntry inEntry) {

        int rc = ProxyRuleUtil.updateProxyEntry(inEntry);

        return Response.status(rc).build();
    }
}
