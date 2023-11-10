package com.zendesk.marcie.data;

import io.vertx.core.Future;

public interface TicketService {

  Future<Ticket> byId(String id);

  Future<TicketData> tickets();

}
