package com.zendesk.marcie.http;

import com.zendesk.marcie.data.Comment;
import com.zendesk.marcie.data.CommentData;
import com.zendesk.marcie.data.CommentService;
import com.zendesk.marcie.data.Ticket;
import com.zendesk.marcie.data.TicketData;
import com.zendesk.marcie.data.TicketService;
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
  @PUT
  @Path("/tickets/{id}")
  public Future<Ticket> addComment(@PathParam("id") String id, Comment comment) {
    return commentService.addComment(id, comment);
  }

  @SuppressWarnings("MissingJavadocMethod")
  @Path("/tickets/{id}")
  @GetOne(produces = Ticket.class)
  public Future<Ticket> ticketById(@PathParam("id") String id) {
    return ticketService.byId(id);
  }

  @SuppressWarnings("MissingJavadocMethod")
  @Path("/tickets")
  @GetOne(produces = TicketData.class)
  public Future<TicketData> tickets() {
    return ticketService.tickets();
  }

  @SuppressWarnings("MissingJavadocMethod")
  @Path("/tickets/{id}/comments")
  @GetOne(produces = CommentData.class)
  public Future<CommentData> ticketComments(@PathParam("id") String id) {
    return commentService.comments(id);
  }

  @NotFoundMessageProducer
  public String notFoundMessage() {
    return "Resource could not be found";
  }
}
