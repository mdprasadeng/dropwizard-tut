package com.mdprasadeng.jersey;

import com.google.inject.Inject;

import com.mdprasadeng.app.models.User;
import com.mdprasadeng.app.service.UserService;

import java.util.Optional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

  private final UserService userService;

  @Inject
  public UserResource(UserService userService) {
    this.userService = userService;
  }

  @GET
  @Path("/{phoneNumber}")
  @UnitOfWork
  public User getUser(@PathParam("phoneNumber") String phoneNumber) {
    Optional<User> user = userService.fetchUser(phoneNumber);
    if (user.isPresent()){
      return user.get();
    } else {
      throw new NotFoundException("User with phoneNumber " + phoneNumber + " doesn't exist");
    }

//    return user.orElseThrow(
//        () -> new NotFoundException("User with phoneNumber " + phoneNumber + " doesn't exist"));
  }


  @PUT
  @Path("/{phoneNumber}")
  @UnitOfWork
  public User createOrUpdateUser(@PathParam("phoneNumber") String phoneNumber, User user) {
    if (!phoneNumber.equals(user.getPhoneNumber())) {
      throw new BadRequestException("Mismatching phone numbers");
    }
    userService.saveUser(user);
    return user;
  }

}
