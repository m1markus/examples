package ch.m1m.quarkus.rest1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.TimeUnit;

@Path("/hello")
public class ExampleResource {

    private static Logger LOG = LoggerFactory.getLogger(ExampleResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {

        LOG.info("before hello");

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            LOG.warn("sleep() got interrupted", e);
        }

        LOG.info("after hello");
        return "hello";
    }
}