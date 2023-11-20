package com.zendesk.marcie.health;

import com.zendesk.core.health.AsyncHealthCheck;
import com.zendesk.core.health.ZendeskHealthCheck;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ZendeskHealthCheck(resource = "https://jsonplaceholder.typicode.com/")
public class MarcieHealthCheck implements AsyncHealthCheck {

  private final WebClient webClient;

  @Inject
  public MarcieHealthCheck(Vertx vertx) {
    webClient = WebClient.create(vertx,
        new WebClientOptions().setDefaultHost("jsonplaceholder.typicode.com"));
  }

  @Override
  public Future<HealthCheckResponse> call() {
    final var builder = HealthCheckResponse.builder().name("json api");
    final var request = webClient.get("");
    return request.expect(ResponsePredicate.SC_OK).send()
        .map(r -> builder.up().build())
        .otherwise(t -> builder.down().build());
  }
}
