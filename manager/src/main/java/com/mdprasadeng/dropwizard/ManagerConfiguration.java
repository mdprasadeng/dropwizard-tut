package com.mdprasadeng.dropwizard;

import io.dropwizard.Configuration;

public class ManagerConfiguration extends Configuration{

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
