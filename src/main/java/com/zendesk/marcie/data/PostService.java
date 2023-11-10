package com.zendesk.marcie.data;

import io.vertx.core.Future;
import java.util.List;

public interface PostService {

  Future<Post> byId(String id);

  Future<List<Post>> posts();
}
