package com.mdprasadeng.phonenetworkclient;

import com.mdprasadeng.phonenetworkclient.internal.PhoneNetworkClientImpl;

import javax.ws.rs.client.Client;

public class PhoneNetworkClientFactory {

  public static PhoneNetworkClient create(Client client, PhoneNetworkClientConfiguration configuration) {
    return new PhoneNetworkClientImpl(client, configuration);
  }
}
