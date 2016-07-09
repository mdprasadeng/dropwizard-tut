package com.mdprasadeng.app.service;

import com.mdprasadeng.app.models.User;
import com.mdprasadeng.phonenetworkclient.PhoneNetworkClient;
import com.mdprasadeng.phonenetworkclient.models.PhoneNetwork;

import java.util.Optional;

public class PhoneNetworkClientAdapter {

  private final PhoneNetworkClient pnClient;

  public PhoneNetworkClientAdapter(PhoneNetworkClient pnClient) {
    this.pnClient = pnClient;
  }

  public Optional<User.PhoneNetwork> getPhoneNetwork(String phoneNumber) throws Exception {
    Optional<PhoneNetwork> phoneNetwork = pnClient.getPhoneNetwork(phoneNumber);
    return phoneNetwork.map(this::phoneNetworkToUserPhoneNetwork);
  }

  public void savePhoneNetwork(String phoneNumber, User.PhoneNetwork userPN) throws Exception {
    pnClient.savePhoneNetwork(phoneNumber, userPhoneNetworkToPhoneNetwork(userPN));
  }

  private User.PhoneNetwork phoneNetworkToUserPhoneNetwork(PhoneNetwork phoneNetwork) {
    User.PhoneNetwork userPN = new User.PhoneNetwork();
    userPN.setProvider(phoneNetwork.getNetworkProvider());
    userPN.setState(phoneNetwork.getNetworkState());
    return userPN;
  }

  private PhoneNetwork userPhoneNetworkToPhoneNetwork(User.PhoneNetwork userPN) {
    PhoneNetwork phoneNetwork = new PhoneNetwork();
    phoneNetwork.setNetworkProvider(userPN.getProvider());
    phoneNetwork.setNetworkState(userPN.getState());
    return phoneNetwork;
  }


}
