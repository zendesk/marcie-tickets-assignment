package com.zendesk.marcie.data;

import com.zendesk.core.caching.CacheKey;
import com.zendesk.core.caching.CacheResult;
import com.zendesk.core.metrics.microprofile.CountedFailure;
import com.zendesk.core.metrics.microprofile.CountedSuccess;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import jakarta.enterprise.inject.Typed;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Typed(TicketService.class)
class TicketServiceImpl implements TicketService {

  private static final String contentType = "content-type";
  private static final String applcationJson = "application/json";
  private final WebClient client;
  private final String username = System.getenv("USERNAME");
  private final String password = System.getenv("PASSWORD");

  @Inject
  @VertxWebClient
  public TicketServiceImpl(WebClient client) {
    this.client = client;
  }

  @CacheResult(cacheName = "tickets_cache")
  @Retry(maxRetries = 5)
  @Timeout(5000) //timeout after 5 seconds
  @Timed(name = "tickets.api.time", absolute = true)
  @CountedFailure(name = "tickets.api.failure", absolute = true)
  @CountedSuccess(name = "tickets.api.success", absolute = true)
  @Override
  public Future<Ticket> byId(@CacheKey String ticketId) {
    return client.get("/api/v2/tickets/" + ticketId).putHeader(contentType, applcationJson)
        .basicAuthentication(username, password).send().compose(res -> {
          if (res.statusCode() == 200) {
            return Future.succeededFuture(
                res.bodyAsJsonObject().getJsonObject("ticket").mapTo(Ticket.class));
          } else {
            return Future.failedFuture("HTTP request failed with status code: " + res.statusCode());
          }
        });
  }


  @CacheResult(cacheName = "tickets_cache")
  @Retry(maxRetries = 5)
  @Timeout(5000) //timeout after 5 seconds
  @Timed(name = "tickets.api.time", absolute = true)
  @CountedFailure(name = "tickets.api.failure", absolute = true)
  @CountedSuccess(name = "tickets.api.success", absolute = true)
  @Override
  public Future<Ticket> addComment(@CacheKey String ticketId, TicketComment comment) {
    JsonObject commentJson = new JsonObject().put("ticket",
        new JsonObject().put("comment", new JsonObject().put("body", comment.body())));
    return client.put("/api/v2/tickets/" + ticketId + ".json")
        .putHeader(contentType, applcationJson).basicAuthentication(username, password)
        .sendJsonObject(commentJson).compose(res -> {
          if (res.statusCode() == 200 || res.statusCode() == 201) {
            var resObj = res.bodyAsJsonObject().getJsonObject("ticket");
            return Future.succeededFuture(resObj.mapTo(Ticket.class));
          } else {
            return Future.failedFuture(
                "Add comment request failed with status code: " + res.statusCode());
          }
        });
  }

  @CacheResult(cacheName = "tickets_cache")
  @Retry(maxRetries = 5)
  @Timeout(5000) //timeout after 5 seconds
  @Timed(name = "tickets.api.time", absolute = true)
  @CountedFailure(name = "tickets.api.failure", absolute = true)
  @CountedSuccess(name = "tickets.api.success", absolute = true)
  @Override
  public Future<TicketData> tickets() {
    return client.get("/api/v2/tickets").putHeader(contentType, applcationJson)
        .basicAuthentication(username, password).send().compose(res -> {
          if (res.statusCode() == 200) {
            return Future.succeededFuture(res.bodyAsJsonObject().mapTo(TicketData.class));
          } else {
            return Future.failedFuture("List ticket failed with status code" + res.statusCode());
          }
        });
  }

  @CacheResult(cacheName = "tickets_cache")
  @Retry(maxRetries = 5)
  @Timeout(5000) //timeout after 5 seconds
  @Timed(name = "tickets.api.time", absolute = true)
  @CountedFailure(name = "tickets.api.failure", absolute = true)
  @CountedSuccess(name = "tickets.api.success", absolute = true)
  @Override
  public Future<CommentData> comments(String ticketId) {

    return client.get("/api/v2/tickets/" + ticketId + "/comments")
        .putHeader(contentType, applcationJson).basicAuthentication(username, password).send()
        .compose(res -> {
          if (res.statusCode() == 200) {
            return Future.succeededFuture(res.bodyAsJsonObject().mapTo(CommentData.class));

          } else {
            return Future.failedFuture("Failed to fetch comment " + res.statusCode());
          }
        });
  }
}
