package com.mdprasadeng.phonenetworkclient;

import com.mdprasadeng.phonenetworkclient.models.PhoneNetwork;

import java.util.Optional;

public interface PhoneNetworkClient {

  Optional<PhoneNetwork> getPhoneNetwork(String phoneNumber) throws Exception;

  void savePhoneNetwork(String phoneNumber, PhoneNetwork phoneNetwork) throws Exception;
}
