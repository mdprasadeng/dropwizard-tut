package com.mdprasadeng.dropwizard;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mdprasadeng.app.service.UserService;
import com.mdprasadeng.hibernate.UserEntityDAO;
import com.mdprasadeng.hibernate.entities.UserEntity;
import com.mdprasadeng.jersey.HelloResource;
import com.mdprasadeng.jersey.UserResource;

import org.glassfish.jersey.filter.LoggingFilter;

import javax.ws.rs.client.Client;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ManagerApplication extends Application<ManagerConfiguration> {

  private HibernateBundle<ManagerConfiguration> hibernateBundle;

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

    hibernateBundle =
        new HibernateBundle<ManagerConfiguration>(UserEntity.class) {
          @Override
          public PooledDataSourceFactory getDataSourceFactory(ManagerConfiguration configuration) {
            return configuration.getDataSource();
          }
        };

    bootstrap.addBundle(hibernateBundle);

    bootstrap.addBundle(new MigrationsBundle<ManagerConfiguration>() {
      @Override
      public PooledDataSourceFactory getDataSourceFactory(ManagerConfiguration configuration) {
        return configuration.getDataSource();
      }
    });

  }

  @Override
  public void run(ManagerConfiguration configuration, Environment environment) throws Exception {

    environment.jersey(); // gives access to Jersey
    environment.getObjectMapper(); //gives access to Jackson

    environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    UserService userService = new UserService(new UserEntityDAO(hibernateBundle.getSessionFactory()));

    Client client = new JerseyClientBuilder(environment)
        .build("client");

    client.register(new LoggingFilter());

    environment.jersey().register(new HelloResource(configuration));
    environment.jersey().register(new UserResource(userService, client));

  }
}
