package com.mdprasadeng.app.models;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.Data;

@Data
@JsonSnakeCase
public class User {

  private String phoneNumber;
  private String emailId;
  private PhoneNetwork network;

  @Data
  @JsonSnakeCase
  public static class PhoneNetwork {
    private String state;
    private String provider;
  }
}
