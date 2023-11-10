package com.zendesk.marcie.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zendesk.core.caching.CacheKey;
import com.zendesk.core.caching.CacheResult;
import com.zendesk.core.metrics.microprofile.CountedFailure;
import com.zendesk.core.metrics.microprofile.CountedSuccess;
import com.zendesk.core.webclient.ClientRequest;
import com.zendesk.core.webclient.WebClient;
import io.vertx.core.Future;
import io.vertx.core.json.jackson.JacksonCodec;
import jakarta.enterprise.inject.Typed;
import jakarta.inject.Inject;
import java.util.List;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Typed(PostService.class)
class PostServiceImpl implements PostService {

  private final WebClient client;

  @Inject
  @ZendeskWebClient
  public PostServiceImpl(WebClient client) {
    this.client = client;
  }

  @CacheResult(cacheName = "posts_cache")
  @Retry(maxRetries = 5)
  @Timeout(5000) //timeout after 5 seconds
  @Timed(name = "posts.api.time", absolute = true)
  @CountedFailure(name = "posts.api.failure", absolute = true)
  @CountedSuccess(name = "posts.api.success", absolute = true)
  @Override
  public Future<Post> byId(@CacheKey String id) {
    client.context().settings().secret("");
    return client.get(ClientRequest.forRoute("/posts/" + id)).map(r -> r.body().mapTo(Post.class));
  }

  @CacheResult(cacheName = "posts_cache")
  @Retry(maxRetries = 5)
  @Timeout(5000) //timeout after 5 seconds
  @Timed(name = "posts.api.time", absolute = true)
  @CountedFailure(name = "posts.api.failure", absolute = true)
  @CountedSuccess(name = "posts.api.success", absolute = true)
  @Override
  public Future<List<Post>> posts() {
    return client.execute(ClientRequest.forJsonArray("/posts").build(),
        response -> JacksonCodec.decodeValue(response.body().toBuffer(), new TypeReference<>() {
        }));
  }
}
