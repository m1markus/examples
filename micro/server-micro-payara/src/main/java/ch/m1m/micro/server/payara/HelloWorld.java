package ch.m1m.micro.server.payara;


import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@ApplicationScoped
@Path("/hello")
@Produces(TEXT_PLAIN)
public class HelloWorld {

    @GET
    @Path("/world")
    public String resHelloWorld() {
        return "Hello World from payara-micro\n";
    }
}