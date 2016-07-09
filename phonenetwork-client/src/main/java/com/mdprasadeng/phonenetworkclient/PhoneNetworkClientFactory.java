package com.mdprasadeng.phonenetworkclient;

import com.mdprasadeng.phonenetworkclient.internal.PhoneNetworkClientHystrixImpl;

import javax.ws.rs.client.Client;

public class PhoneNetworkClientFactory {

  public static PhoneNetworkClient create(Client client, PhoneNetworkClientConfiguration configuration) {
    return new PhoneNetworkClientHystrixImpl(client, configuration);
  }
}
