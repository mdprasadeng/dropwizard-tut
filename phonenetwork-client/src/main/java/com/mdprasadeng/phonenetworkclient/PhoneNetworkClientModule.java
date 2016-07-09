package com.mdprasadeng.phonenetworkclient;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import com.mdprasadeng.phonenetworkclient.internal.PhoneNetworkClientHystrixImpl;

public class PhoneNetworkClientModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(PhoneNetworkClient.class).to(PhoneNetworkClientHystrixImpl.class).in(Singleton.class);
  }
}
