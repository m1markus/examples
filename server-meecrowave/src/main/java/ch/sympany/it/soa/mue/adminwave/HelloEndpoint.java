package ch.sympany.it.soa.mue.adminwave;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("hello")
@ApplicationScoped
public class HelloEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String sayHi() {
        return "Hello World";
    }
}