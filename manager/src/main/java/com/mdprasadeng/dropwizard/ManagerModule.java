package com.mdprasadeng.dropwizard;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.mdprasadeng.phonenetworkclient.PhoneNetworkClientConfiguration;

import org.glassfish.jersey.filter.LoggingFilter;
import org.hibernate.SessionFactory;

import java.util.logging.Logger;

import javax.ws.rs.client.Client;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.setup.Environment;

public class ManagerModule extends AbstractModule{

  private final ManagerConfiguration managerConfiguration;
  private final Environment environment;
  private final SessionFactory sessionFactory;

  public ManagerModule(ManagerConfiguration managerConfiguration, Environment environment,
                       SessionFactory sessionFactory) {
    this.managerConfiguration = managerConfiguration;
    this.environment = environment;
    this.sessionFactory = sessionFactory;
  }

  @Override
  protected void configure() {
    bind(ManagerConfiguration.class).toInstance(managerConfiguration);
    bind(PhoneNetworkClientConfiguration.class).toInstance(managerConfiguration.getPnConfig());
    bind(DataSourceFactory.class).toInstance(managerConfiguration.getDataSource());
    bind(SessionFactory.class).toInstance(sessionFactory);
  }

  @Provides
  @Singleton
  private Client getClient() {
    Client client = new JerseyClientBuilder(environment)
        .using(managerConfiguration.getJerseyClient())
        .build("client");
    client.register(new LoggingFilter(Logger.getLogger("ClientLogger"), true));
    return client;
  }




}
