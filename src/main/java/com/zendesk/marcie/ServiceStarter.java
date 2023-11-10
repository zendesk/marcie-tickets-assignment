package com.zendesk.marcie;

import com.zendesk.core.config.VertxTuning;
import com.zendesk.core.http.HttpVerticleBuilder;
import com.zendesk.core.starter.StarterApp;
import com.zendesk.core.starter.allocation.VerticlePriority;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Starts the marcie-ticket-ds.
 */
public class ServiceStarter {

  /**
   * main method.
   *
   * @param args String[]
   */
  public static void main(String[] args) {

    VertxTuning.httpOnlyServer();

    if (System.getenv("USERNAME") == null || System.getenv("PASSWORD") == null) {
      System.err.println(
          "Stopped due to error: USERNAME and PASSWORD environment variables must be set!");
      System.exit(1);
    }

    final var appInstance = StarterApp
        .create("example-api-server", args)
        .withVerticle(HttpVerticleBuilder.from(MarcieHttpModule.class)
            .withInsights()
            .withBodyMiddleware()
            .with(VerticlePriority.HIGH))
        .withVerticle(MarcieVerticleBuilder.from(new MarcieModule()))
        .create();
    appInstance.run();
    // [dz] example of how to schedule shutdown for 24 hours
    Executors.newScheduledThreadPool(1).schedule(() -> {
      try {
        appInstance.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }, 24, TimeUnit.HOURS);
  }
}
