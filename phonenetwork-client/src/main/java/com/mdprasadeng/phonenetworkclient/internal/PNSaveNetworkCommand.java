package com.mdprasadeng.phonenetworkclient.internal;

import com.mdprasadeng.phonenetworkclient.PhoneNetworkClientConfiguration;
import com.mdprasadeng.phonenetworkclient.models.PhoneNetwork;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PNSaveNetworkCommand extends HystrixCommand<Void> {

  private final Client client;
  private final PhoneNetworkClientConfiguration configuration;
  private final String phoneNumber;
  private final PhoneNetwork phoneNetwork;

  public PNSaveNetworkCommand(HystrixCommandGroupKey group,
                              int executionIsolationThreadTimeoutInMilliseconds, Client client,
                              PhoneNetworkClientConfiguration configuration, String phoneNumber,
                              PhoneNetwork phoneNetwork) {
    super(group, executionIsolationThreadTimeoutInMilliseconds);
    this.client = client;
    this.configuration = configuration;
    this.phoneNumber = phoneNumber;
    this.phoneNetwork = phoneNetwork;
  }


  @Override
  protected Void run() throws Exception {
    Response response = client.target(configuration.getUrl())
        .path(configuration.getPhonePath())
        .path(phoneNumber + ".json")
        .request()
        .accept(MediaType.APPLICATION_JSON_TYPE)
        .put(Entity.json(phoneNetwork));

    response.close();
    if (response.getStatus() != 200) {
      throw new Exception("PhoneNetwork threw Response code:" + response.getStatus());
    }
    return null;
  }
}
