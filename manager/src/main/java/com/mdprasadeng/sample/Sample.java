package com.mdprasadeng.sample;

public class Sample {

  public static void main(String[] args) {
    System.out.println("Hello from a Java Program");
    for (int i=0; i < args.length; i++) {
      System.out.println("args[" + i + "]=" + args[i]);
    }
  }
}
