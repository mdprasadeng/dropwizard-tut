package com.mdprasadeng.jersey;

import com.mdprasadeng.app.models.User;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

  @GET
  @Path("/{phoneNumber}")
  public User getUser(@PathParam("phoneNumber") String phoneNumber) {
    User user = new User();
    user.setEmailId("durga.p@flipkart.com");
    user.setPhoneNumber("99999999999");
    return user;
  }


  @PUT
  @Path("/{phoneNumber}")
  public User createOrUpdateUser(@PathParam("phoneNumber") String phoneNumber, User user) {
    if (!phoneNumber.equals(user.getPhoneNumber())) {
      throw new BadRequestException("Mismatching phone numbers");
    }
    return user;
  }

}
