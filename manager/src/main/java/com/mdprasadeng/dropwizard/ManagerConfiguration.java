package com.mdprasadeng.dropwizard;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Data;

@Data
public class ManagerConfiguration extends Configuration{

  private String name;
  private DataSourceFactory dataSource;


}
