package com.mdprasadeng.dropwizard;

import com.mdprasadeng.jersey.HelloResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ManagerApplication extends Application<ManagerConfiguration> {

  public static void main(String[] args) throws Exception {
    System.out.println("Hello from a Java Program");
    for (int i=0; i < args.length; i++) {
      System.out.println("args[" + i + "]=" + args[i]);
    }
    new ManagerApplication().run(args);
  }

  @Override
  public void initialize(Bootstrap<ManagerConfiguration> bootstrap) {
    super.initialize(bootstrap);
  }

  @Override
  public void run(ManagerConfiguration configuration, Environment environment) throws Exception {

    environment.jersey(); // gives access to Jersey
    environment.getObjectMapper(); //gives access to Jackson

    environment.jersey().register(new HelloResource(configuration));

  }
}
