package com.mdprasadeng.phonenetworkclient;

import lombok.Data;

@Data
public class PhoneNetworkClientConfiguration {
  private String url;
  private String phonePath = "phone";
}
