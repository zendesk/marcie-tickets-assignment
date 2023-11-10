package com.zendesk.marcie.data;

import com.zendesk.core.caching.CacheKey;
import com.zendesk.core.caching.CacheResult;
import com.zendesk.core.metrics.microprofile.CountedFailure;
import com.zendesk.core.metrics.microprofile.CountedSuccess;
import io.vertx.core.Future;
import io.vertx.ext.web.client.WebClient;
import jakarta.enterprise.inject.Typed;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Typed(TicketService.class)
class TicketServiceImpl extends BaseService implements TicketService {

  @Inject
  @VertxWebClient
  public TicketServiceImpl(WebClient client) {
    super(client);
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
           System.out.println(res.bodyAsJsonObject().encodePrettily());
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
  public Future<TicketData> tickets() {
    return client.get("/api/v2/tickets").putHeader(contentType, applcationJson)
        .basicAuthentication(username, password).send().compose(res -> {
          if (res.statusCode() == 200) {
            System.out.println(res.bodyAsJsonObject().encodePrettily());
            return Future.succeededFuture(res.bodyAsJsonObject().mapTo(TicketData.class));
          } else {
            return Future.failedFuture("List ticket failed with status code" + res.statusCode());
          }
        });
  }
}
