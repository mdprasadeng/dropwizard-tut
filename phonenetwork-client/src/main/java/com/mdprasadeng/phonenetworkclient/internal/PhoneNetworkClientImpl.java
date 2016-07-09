package com.mdprasadeng.phonenetworkclient.internal;

import com.mdprasadeng.phonenetworkclient.PhoneNetworkClient;
import com.mdprasadeng.phonenetworkclient.PhoneNetworkClientConfiguration;
import com.mdprasadeng.phonenetworkclient.models.PhoneNetwork;

import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PhoneNetworkClientImpl implements PhoneNetworkClient {

  private final Client client;
  private final PhoneNetworkClientConfiguration configuration;

  public PhoneNetworkClientImpl(Client client, PhoneNetworkClientConfiguration configuration) {
    this.client = client;
    this.configuration = configuration;
  }

  @Override
  public Optional<PhoneNetwork> getPhoneNetwork(String phoneNumber) throws Exception{
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

  @Override
  public void savePhoneNetwork(String phoneNumber, PhoneNetwork phoneNetwork) throws Exception {
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
  }
}
