package com.zendesk.marcie.http;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import io.vertx.core.http.HttpServerOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MarcieConfiguratorTest {

  private MarcieConfigurator configurator;
  private HttpServerOptions options;

  @BeforeEach
  public void setup() {
    configurator = new MarcieConfigurator();
    options = Mockito.spy(HttpServerOptions.class);
  }

  @Test
  public void testApply() {
    when(options.isCompressionSupported()).thenReturn(true);
    HttpServerOptions returnedOptions = configurator.apply(options);
    assertTrue(returnedOptions.isCompressionSupported());
    Mockito.verify(options).setCompressionSupported(true);
  }
}