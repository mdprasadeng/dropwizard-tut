package com.mdprasadeng.phonenetworkclient.internal;

import com.mdprasadeng.phonenetworkclient.PhoneNetworkClient;
import com.mdprasadeng.phonenetworkclient.PhoneNetworkClientConfiguration;
import com.mdprasadeng.phonenetworkclient.models.PhoneNetwork;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.Optional;

import javax.ws.rs.client.Client;

public class PhoneNetworkClientHystrixImpl implements PhoneNetworkClient {

  private final Client client;
  private final PhoneNetworkClientConfiguration configuration;

  public PhoneNetworkClientHystrixImpl(Client client,
                                       PhoneNetworkClientConfiguration configuration) {
    this.client = client;
    this.configuration = configuration;
  }

  @Override
  public Optional<PhoneNetwork> getPhoneNetwork(String phoneNumber) throws Exception {
    HystrixCommand<Optional<PhoneNetwork>>
        hystrixCommand =
        new PNFetchNetworkCommand(getGroupKey(), 5000, client, configuration,
                                  phoneNumber);
    return hystrixCommand.execute();
  }

  private HystrixCommandGroupKey getGroupKey() {
    return HystrixCommandGroupKey.Factory.asKey("pn-client");
  }

  @Override
  public void savePhoneNetwork(String phoneNumber, PhoneNetwork phoneNetwork) throws Exception {
    HystrixCommand<Void>
        hystrixCommand =
        new PNSaveNetworkCommand(getGroupKey(), 5000, client, configuration, phoneNumber,
                                 phoneNetwork);
    hystrixCommand.execute();
  }
}
