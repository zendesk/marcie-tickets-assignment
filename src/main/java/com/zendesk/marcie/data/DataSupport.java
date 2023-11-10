package com.zendesk.marcie.data;

import com.zendesk.core.caching.StarterCachingExtension;
import com.zendesk.core.inject.StarterExtension;
import com.zendesk.core.logging.ZendeskLogger;
import com.zendesk.core.resilience.BreakerOptions;
import com.zendesk.core.starter.StarterContext;
import com.zendesk.core.webclient.WebClient;
import com.zendesk.core.webclient.WebClientBuilder;
import com.zendesk.starter.weld.StarterWeldExtension;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClientOptions;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import java.net.URI;
import java.util.Set;
import java.util.function.Supplier;
import org.jboss.weld.environment.se.Weld;

public class DataSupport implements StarterWeldExtension {

  @Override
  public void configure(Weld weld) {
    ZendeskLogger.forEnclosingClass().atInfo().log("Loading Data Support beans");
    weld.addBeanClasses(PostServiceImpl.class, CommentServiceImpl.class, TicketServiceImpl.class);
  }

  @Override
  public Set<Class<? extends StarterExtension>> extensions() {
    return Set.of(DataSupport.class, StarterCachingExtension.class);
  }

  /**
   * Builds a {@link WebClient} that can be shared to connect to our example data.
   *
   * @param context the web context.
   * @return a new {@link WebClient}
   */
  @SuppressWarnings("CloseableProvides")
  @Singleton
  @Produces
  @ZendeskWebClient
  public WebClient provideClient(StarterContext context) {
    context.settings().appEnvironment();
    return WebClientBuilder.create(context).withSsl(true)
        .withBaseUri(URI.create("https://zendeskcodingchallenge9453.zendesk.com/"))
        .withCircuitBreaker(BreakerOptions.withDefaults("json-breaker"))
        .withClientName("jsonplaceholder-client").build()
        .getOrElseThrow((Supplier<RuntimeException>) RuntimeException::new);
  }

  /**
   * Builds a {@link WebClient} that can be shared to connect to our example data.
   *
   * @return a new {@link WebClient}
   */
  @Singleton
  @Produces
  @VertxWebClient
  public io.vertx.ext.web.client.WebClient vertxWebClient() {

    if (System.getenv("SUBDOMAIN") == null || System.getenv("USERNAME") == null
        || System.getenv("PASSWORD") == null) {
      System.err.println(
          "Stopped due to: SUBDOMAIN, USERNAME and PASSWORD environment variables must be set!");
      System.exit(1);
    }

    Vertx vertx = Vertx.vertx();

    WebClientOptions webClientOptions = new WebClientOptions().setDefaultHost(
            System.getenv("SUBDOMAIN") + ".zendesk.com").setSsl(true).setDefaultPort(443)
        .setTrustAll(true);
    return io.vertx.ext.web.client.WebClient.create(vertx, webClientOptions);
  }

}
