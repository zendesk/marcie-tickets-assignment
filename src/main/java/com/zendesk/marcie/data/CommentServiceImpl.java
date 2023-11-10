package com.zendesk.marcie.data;

import com.zendesk.core.caching.CacheKey;
import com.zendesk.core.caching.CacheResult;
import com.zendesk.core.metrics.microprofile.CountedFailure;
import com.zendesk.core.metrics.microprofile.CountedSuccess;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.annotation.Timed;

class CommentServiceImpl extends BaseService implements CommentService {

  @Inject
  public CommentServiceImpl(WebClient client) {
    super(client);
  }


  @CacheResult(cacheName = "tickets_cache")
  @Retry(maxRetries = 5)
  @Timeout(5000) //timeout after 5 seconds
  @Timed(name = "tickets.api.time", absolute = true)
  @CountedFailure(name = "tickets.api.failure", absolute = true)
  @CountedSuccess(name = "tickets.api.success", absolute = true)
  @Override
  public Future<Ticket> addComment(@CacheKey String ticketId, Comment comment) {
    JsonObject commentJson = new JsonObject().put("ticket",
        new JsonObject().put("comment", new JsonObject().put("body", comment.body())));
    return client.put("/api/v2/tickets/" + ticketId + ".json")
        .putHeader(contentType, applcationJson).basicAuthentication(username, password)
        .sendJsonObject(commentJson).compose(res -> {
          if (res.statusCode() == 200 || res.statusCode() == 201) {

            var resObj = res.bodyAsJsonObject().getJsonObject("ticket");
            System.out.println(resObj.encodePrettily());
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
  public Future<CommentData> comments(String ticketId) {

    return client.get("/api/v2/tickets/" + ticketId + "/comments")
        .putHeader(contentType, applcationJson).basicAuthentication(username, password).send()
        .compose(res -> {
          if (res.statusCode() == 200) {
            System.out.println(res.bodyAsJsonObject().encodePrettily());
            return Future.succeededFuture(res.bodyAsJsonObject().mapTo(CommentData.class));

          } else {
            return Future.failedFuture("Failed to fetch comment " + res.statusCode());
          }
        });
  }
}