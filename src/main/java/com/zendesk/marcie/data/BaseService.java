package com.zendesk.marcie.data;

import io.vertx.ext.web.client.WebClient;

public class BaseService {

  protected static final String contentType = "content-type";
  protected static final String applcationJson = "application/json";
  protected final WebClient client;
  protected final String username = System.getenv("USERNAME");
  protected final String password = System.getenv("PASSWORD");

  public BaseService(WebClient client) {
    this.client = client;
  }
}
