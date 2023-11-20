package com.zendesk.marcie.http;

import com.zendesk.resteasy.RestEasyResource;
import io.vertx.core.Future;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/api")
public class GreeterController implements RestEasyResource {

  @GET
  @Path("/hello")
  public Future<String> sayHello(@DefaultValue("Dattatray") @QueryParam("name") String name) {
    //if(name == null) name = "Dattatray";
    return Future.succeededFuture("Hello, " + name);
  }
}
