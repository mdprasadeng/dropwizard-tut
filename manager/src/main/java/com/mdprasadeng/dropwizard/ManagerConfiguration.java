package com.mdprasadeng.dropwizard;

import com.mdprasadeng.phonenetworkclient.PhoneNetworkClientConfiguration;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Data;

@Data
public class ManagerConfiguration extends Configuration{

  private String name;
  private PhoneNetworkClientConfiguration pnConfig;
  private JerseyClientConfiguration jerseyClient;

  private DataSourceFactory dataSource;

}
