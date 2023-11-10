package com.zendesk.marcie.data;

import io.vertx.core.Future;
import java.util.List;

public interface CommentService {

  Future<Ticket> addComment(String id, Comment comment);

  Future<CommentData> comments(String ticketId);
}
