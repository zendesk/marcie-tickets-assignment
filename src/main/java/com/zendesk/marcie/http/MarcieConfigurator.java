package com.zendesk.marcie.http;

import com.zendesk.core.http.HttpServerConfigurator;
import io.vertx.core.http.HttpServerOptions;

public class MarcieConfigurator implements HttpServerConfigurator {

  @Override
  public HttpServerOptions apply(HttpServerOptions options) {
    // example configurator that turns on compression
    return options.setCompressionSupported(true);
  }
}
