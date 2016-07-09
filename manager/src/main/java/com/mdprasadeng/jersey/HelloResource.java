package com.mdprasadeng.jersey;

import com.google.inject.Inject;

import com.mdprasadeng.dropwizard.ManagerConfiguration;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/hello")
public class HelloResource {

  private final ManagerConfiguration managerConfiguration;

  @Inject
  public HelloResource(ManagerConfiguration managerConfiguration) {
    this.managerConfiguration = managerConfiguration;
  }

  @GET
  public String welcome(@QueryParam("name") @DefaultValue("stranger") String name) {
    return "Hello " + name + ", Welcome to " + managerConfiguration.getName() + " Application";
  }
}
