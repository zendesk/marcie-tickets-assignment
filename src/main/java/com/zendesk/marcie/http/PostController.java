package com.zendesk.marcie.http;

import com.zendesk.core.logging.AsyncDiagnosticContext;
import com.zendesk.marcie.data.Comment;
import com.zendesk.marcie.data.CommentService;
import com.zendesk.marcie.data.Post;
import com.zendesk.marcie.data.PostService;
import com.zendesk.marcie.data.TicketData;
import com.zendesk.marcie.data.TicketService;
import com.zendesk.resteasy.RestEasyResource;
import com.zendesk.resteasy.ext.GetMany;
import com.zendesk.resteasy.ext.GetOne;
import com.zendesk.resteasy.ext.NotFoundMessageProducer;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import lombok.RequiredArgsConstructor;


@SuppressWarnings("NullAway")
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class PostController implements RestEasyResource {

  private final PostService postService;
  private final CommentService commentService;
  private final TicketService ticketService;

  @Context
  private AsyncDiagnosticContext loggingContext;

  @Path("/comments")
  @GetMany(produces = Comment.class)
  public Future<List<Comment>> comments(@QueryParam("postId") String postId) {
    loggingContext.put("postId", postId);
    return commentService.byId(postId);
  }

  @SuppressWarnings("MissingJavadocMethod")
  @Path("/posts/{id}")
  @GetOne(produces = Post.class)
  public Future<Post> postById(@PathParam("id") String id,
      @DefaultValue("false") @QueryParam("full") boolean full) {
    loggingContext.put("fullRequest", full).put("postId", id);
    if (full) {
      return CompositeFuture.all(postService.byId(id), commentService.byId(id)).map(r ->
          ((Post) r.resultAt(0)).withComments(r.resultAt(1)));
    }
    return postService.byId(id);
  }

  @SuppressWarnings("MissingJavadocMethod")
  @Path("/posts")
  @GetMany(produces = Post.class)
  public Future<List<Post>> posts() {
    return postService.posts();
  }

  @SuppressWarnings("MissingJavadocMethod")
  @Path("/tickets")
  @GetOne(produces = TicketData.class)
  public Future<TicketData> tickets() {
    return ticketService.tickets();
  }

  @NotFoundMessageProducer
  public String notFoundMessage() {
    return "Resource could not be found";
  }
}
