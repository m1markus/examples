/*
https://docs.oracle.com/javaee/7/tutorial/jaxrs002.htm#GILIK

*/

package ch.m1m.jwt;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("v1")
public class RestMethods {
	
	// url: http://localhost:8080/web1/api/v1/version
	//
	@Path("version")
	@GET
	@Produces("text/html")
	public String getHtml() {
	    return "<html lang=\"en\"><body><h1>rest api Hello v1</h1></body></html>";
	}
	
	
	@Path("json")
	@GET
	@Produces("text/plain")
	public JsonObject getJsonOne() {		
		return Json.createObjectBuilder().add("version",  "v1-rc-1").build();		
	}
	
	
	@Path("object")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getObjectGetSet() {
		ObjectGetSet result = new ObjectGetSet();
		result.setName("Markus");		
		return Response.status(Response.Status.OK).entity(result).build();
	
	}
}
