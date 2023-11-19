package com.zendesk.marcie.http;

import com.zendesk.marcie.data.Comment;
import com.zendesk.marcie.data.CommentResult;
import com.zendesk.marcie.data.CommentService;
import com.zendesk.marcie.data.Ticket;
import com.zendesk.marcie.data.TicketResult;
import com.zendesk.marcie.data.TicketService;
import com.zendesk.marcie.data.TicketsResult;
import com.zendesk.resteasy.RestEasyResource;
import com.zendesk.resteasy.ext.GetOne;
import com.zendesk.resteasy.ext.NotFoundMessageProducer;
import io.vertx.core.Future;
import jakarta.inject.Inject;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.annotations.jaxrs.PathParam;


@SuppressWarnings("NullAway")
@Path("/api/v2")
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class TicketController implements RestEasyResource {

  private final TicketService ticketService;
  private final CommentService commentService;

  @SuppressWarnings("MissingJavadocMethod")
  @Path("/tickets/{ticketId}/comments")
  @GetOne(produces = CommentResult.class)
  public Future<CommentResult> ticketComments(@PathParam("ticketId") String ticketId) {
    return commentService.comments(ticketId);
  }

  @SuppressWarnings("MissingJavadocMethod")
  @PUT
  @Path("/tickets/{id}")
  public Future<Ticket> addComment(@PathParam("id") String id, Comment comment) {
    return commentService.addComment(id, comment);
  }

  @SuppressWarnings("MissingJavadocMethod")
  @Path("/tickets/{id}")
  @GetOne(produces = TicketResult.class)
  public Future<TicketResult> ticketById(@PathParam("id") String id) {
    return ticketService.ticketById(id);
  }

  @SuppressWarnings("MissingJavadocMethod")
  @Path("/tickets")
  @GetOne(produces = TicketsResult.class)
  public Future<TicketsResult> tickets() {
    return ticketService.tickets();
  }

  @NotFoundMessageProducer
  public String notFoundMessage() {
    return "Resource could not be found";
  }
}
