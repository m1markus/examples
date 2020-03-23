package org.acme.security.jwt;

import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

@Path("/secured")
@RequestScoped
public class TokenSecuredResource {

    @Inject
    JsonWebToken jwt;

    @GET()
    @Path("all")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String helloAll(@Context SecurityContext ctx) {
        Principal caller =  ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();
        String helloReply = String.format("hello + %s, isSecure: %s, authScheme: %s",
                name, ctx.isSecure(), ctx.getAuthenticationScheme());
        return helloReply;
    }

    /*

    curl -H "Authorization: Bearer
    eyJraWQiOiIvcHJpdmF0ZUtleS5wZW0iLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3F1YXJrdXMuaW8vdXNpbmctand0LXJiYWMiLCJqdGkiOiJhLTEyMyIsInN1YiI6Impkb2UtdXNpbmctand0LXJiYWMiLCJ1cG4iOiJqZG9lQHF1YXJrdXMuaW8iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJqZG9lIiwiYXVkIjoidXNpbmctand0LXJiYWMiLCJiaXJ0aGRhdGUiOiIyMDAxLTA3LTEzIiwicm9sZU1hcHBpbmdzIjp7Imdyb3VwMSI6Ikdyb3VwMU1hcHBlZFJvbGUiLCJncm91cDIiOiJHcm91cDJNYXBwZWRSb2xlIn0sImdyb3VwcyI6WyJFY2hvZXIiLCJUZXN0ZXIiLCJTdWJzY3JpYmVyIiwiZ3JvdXAyIl0sImlhdCI6MTU4NDkxNzI5OSwiYXV0aF90aW1lIjoxNTg0OTE3Mjk5LCJleHAiOjE1ODUwMDM2OTl9.kCobIY0iDX0H-T4SvoZHo2GltRF5Kbr1iGZwLkKIIz_s4_t2Dh0J7Q1NDU8mZdnhpmYo1vTYBlycVsnzj-rX_ymA-O5HG2MoRKaAEZGSq6IuPijsKLs1_zVF-CRJQuF7Oa4nHho8ntKrkscs5rQTL7BgYeaUEPxVAH-aNzEQLxKQke-0LhJ_ksYBZuR3OwGGn_RLfTfgC2_zmdXJCO_Ik2aaHGRo-ZKH8huiw_5MLOGxoje8ZiyBnL7Vuz0abFdhVzyB4sq23rsIsxu4ZA3K1M24rTO52-gTN2CHs2uYjjHoGcRtaDG4-u5M1wuahYolkr8S9b7LI4tCfkU1Eczf9g"
    -v http://localhost:8080/secured/roles

     */

    @GET()
    @Path("roles")
    //@RolesAllowed({"Echoer", "Subscriber"})
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesAllowed(@Context SecurityContext ctx) {
        Principal caller =  ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();
        boolean hasJWT = jwt.getClaimNames() != null;
        String helloReply = String.format("hello + %s, isSecure: %s, authScheme: %s, hasJWT: %b XXX",
                name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJWT);
        return helloReply;
    }
}
