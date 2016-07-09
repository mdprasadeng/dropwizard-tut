package com.mdprasadeng.phonenetworkclient.internal;

import com.mdprasadeng.phonenetworkclient.PhoneNetworkClientConfiguration;
import com.mdprasadeng.phonenetworkclient.models.PhoneNetwork;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PNFetchNetworkCommand extends HystrixCommand<Optional<PhoneNetwork>> {

  private final Client client;
  private final PhoneNetworkClientConfiguration configuration;
  private final String phoneNumber;

  public PNFetchNetworkCommand(HystrixCommandGroupKey group,
                               int executionIsolationThreadTimeoutInMilliseconds, Client client,
                               PhoneNetworkClientConfiguration configuration, String phoneNumber) {
    super(group, executionIsolationThreadTimeoutInMilliseconds);
    this.client = client;
    this.configuration = configuration;
    this.phoneNumber = phoneNumber;
  }


  @Override
  protected Optional<PhoneNetwork> run() throws Exception {
    Response response = client.target(configuration.getUrl())
        .path(configuration.getPhonePath())
        .path(phoneNumber + ".json")
        .request()
        .accept(MediaType.APPLICATION_JSON_TYPE)
        .get();

    if (response.getStatus() != 200) {
      response.close();
      throw new Exception("PhoneNetwork threw Response code:" + response.getStatus());
    }

    response.bufferEntity();
    if (!response.readEntity(String.class).equals("null") ) {
      return Optional.ofNullable(response.readEntity(PhoneNetwork.class));
    } else {
      return Optional.empty();
    }
  }
}
