package com.zendesk.marcie.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zendesk.core.caching.CacheKey;
import com.zendesk.core.caching.CacheResult;
import com.zendesk.core.metrics.microprofile.CountedFailure;
import com.zendesk.core.webclient.ClientRequest;
import com.zendesk.core.webclient.WebClient;
import io.vertx.core.Future;
import io.vertx.core.json.jackson.JacksonCodec;
import jakarta.inject.Inject;
import java.util.List;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.opentracing.Traced;

class CommentServiceImpl implements CommentService {

  @ZendeskWebClient
  private final WebClient client;

  @Inject
  public CommentServiceImpl(WebClient client) {
    this.client = client;
  }

  @CacheResult(cacheName = "comments_cache")
  @Traced(operationName = "comments.api.trace")
  @Timed(name = "comments.api.time", absolute = true)
  @CountedFailure(name = "comments.api.failure", absolute = true)
  @Override
  public Future<List<Comment>> byId(@CacheKey String id) {
    return client.execute(ClientRequest.forJsonArray("/comments").queryParam("postId", id).build(),
        response -> JacksonCodec.decodeValue(response.body().toBuffer(), new TypeReference<>() {
        }));
  }
}