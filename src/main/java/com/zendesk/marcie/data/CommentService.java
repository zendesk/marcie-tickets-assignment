package com.zendesk.marcie.data;

import io.vertx.core.Future;

public interface CommentService {

  Future<Ticket> addComment(String id, CommentTicket comment);

  Future<CommentResult> comments(String ticketId);
}
