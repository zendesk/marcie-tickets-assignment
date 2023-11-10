package com.zendesk.marcie.data;

import io.vertx.core.Future;
import java.util.List;

public interface CommentService {

  Future<List<Comment>> byId(String id);
}
