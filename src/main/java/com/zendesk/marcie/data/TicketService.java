package com.zendesk.marcie.data;

import io.vertx.core.Future;

public interface TicketService {

  Future<Ticket> byId(String id);

  Future<TicketData> tickets();

  Future<Ticket> addComment(String id, TicketComment comment);

  Future<CommentData> comments(String ticketId);
}
