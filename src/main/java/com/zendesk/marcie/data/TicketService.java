package com.zendesk.marcie.data;

import io.vertx.core.Future;

public interface TicketService {

  Future<TicketResult> ticketById(String id);

  Future<TicketsResult> tickets();

}
