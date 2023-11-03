package com.zendesk.template;

import com.zendesk.core.starter.StarterApp;

public class ServiceStarter {

  public static void main(String[] args) {
    StarterApp
        .create("template", args)
        .start();
  }
}
