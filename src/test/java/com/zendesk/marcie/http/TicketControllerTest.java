package com.zendesk.marcie.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.zendesk.marcie.data.Audit;
import com.zendesk.marcie.data.Comment;
import com.zendesk.marcie.data.CommentField;
import com.zendesk.marcie.data.CommentResult;
import com.zendesk.marcie.data.CommentService;
import com.zendesk.marcie.data.CommentTicket;
import com.zendesk.marcie.data.Event;
import com.zendesk.marcie.data.Ticket;
import com.zendesk.marcie.data.TicketResult;
import com.zendesk.marcie.data.TicketService;
import com.zendesk.marcie.data.TicketsResult;
import com.zendesk.marcie.data.Via;
import io.vertx.core.Future;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TicketControllerTest {

  @Mock
  private TicketService ticketService;

  @Mock
  private CommentService commentService;

  @InjectMocks
  private TicketController ticketController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testTicketComments() {
    long commentId = 14823232328850L;
    String commentType = "Comment";
    long commentAuthorId = 14823221557138L;
    String commentBody = "The smoke is very colorful.";
    Via commentVia = new Via("support");
    String commentCreatedAt = "2023-11-19T07:00:29Z";

    Comment comment = new Comment(commentId, commentType, commentAuthorId, commentBody, commentVia,
        commentCreatedAt);

    List<Comment> expectedCommentList = List.of(comment);

    CommentResult expected = new CommentResult(expectedCommentList);

    when(commentService.comments(any(String.class))).thenReturn(Future.succeededFuture(expected));

    Future<CommentResult> result = ticketController.ticketComments("testTicketId");
    assertEquals(expected, result.result());
  }

  @Test
  void testAddComment() {
    String url = "https://subdomain.zendesk.com/api/v2/tickets/3.json";
    int id = 3;
    String subject = "Sample ticket: Meet the ticket";
    Date createdAt = new Date();
    Date updatedAt = new Date();
    String type = "incident";
    long requesterId = 15178084006290L;
    long submitterId = 15178068258322L;
    long assigneeId = 15178068258322L;
    String description = "I’m sending an email because I’m having a problem setting";
    List<String> tags = Arrays.asList("sample",
        "support",
        "zendesk");

    long auditId = 123;
    long auditTicketId = 1;
    String auditCreatedAt = "testAuditCreatedAt";
    long auditAuthorId = 456;
    List<Event> auditEvents = new ArrayList<>();
    Via auditVia = new Via("sample_ticket");
    Audit audit = new Audit(auditId, auditTicketId, auditCreatedAt, auditAuthorId, auditEvents,
        auditVia);

    String status = "open";
    String priority = "normal";


    Ticket expected = new Ticket(url, id, subject, createdAt, updatedAt, type, requesterId,
        submitterId, assigneeId, description, tags, audit, auditVia, status, priority);

    long commentId = 789;
    String commentType = "testCommentType";
    long commentAuthorId = 987;
    String commentBody = "testCommentBody";
    Via commentVia = new Via("support");
    String commentCreatedAt = "testCommentCreatedAt";

    Comment comment = new Comment(commentId, commentType, commentAuthorId, commentBody, commentVia,
        commentCreatedAt);
    CommentField commentField = new CommentField(comment);
    CommentTicket commentTicket = new CommentTicket(commentField);

    when(commentService.addComment(any(String.class), any(CommentTicket.class))).thenReturn(
        Future.succeededFuture(expected));

    Future<Ticket> result = ticketController.addComment("3", commentTicket);
    assertEquals(expected, result.result());
    assertNotNull(result);

  }

  @Test
  void testTicketById() {
    String url = "https://subdomain.zendesk.com/api/v2/tickets/2.json";
    int id = 2;
    String subject = "Sample ticket: Meet the ticket";
    Date createdAt = new Date();
    Date updatedAt = new Date();
    String type = "incident";
    long requesterId = 15178084006290L;
    long submitterId = 15178068258322L;
    long assigneeId = 15178068258322L;
    String description = "I’m sending an email because I’m having a problem setting";
    List<String> tags = Arrays.asList("sample",
        "support",
        "zendesk");

    long auditId = 123;
    long auditTicketId = 1;
    String auditCreatedAt = "testAuditCreatedAt";
    long auditAuthorId = 456;
    List<Event> auditEvents = new ArrayList<>();
    Via auditVia = new Via("sample_ticket");
    Audit audit = new Audit(auditId, auditTicketId, auditCreatedAt, auditAuthorId, auditEvents,
        auditVia);

    String status = "open";
    String priority = "normal";

    Ticket ticket = new Ticket(url, id, subject, createdAt, updatedAt, type, requesterId,
        submitterId, assigneeId, description, tags, audit, auditVia, status, priority);

    TicketResult expected = new TicketResult(ticket);

    when(ticketService.ticketById(any(String.class))).thenReturn(Future.succeededFuture(expected));

    Future<TicketResult> result = ticketController.ticketById("2");
    assertEquals(expected, result.result());
  }

  @Test
  void testTickets() {
    String url = "https://subdomain.zendesk.com/api/v2/tickets/1.json";
    int id = 1;
    String subject = "Sample ticket: Meet the ticket";
    Date createdAt = new Date();
    Date updatedAt = new Date();
    String type = "incident";
    long requesterId = 15178084006290L;
    long submitterId = 15178068258322L;
    long assigneeId = 15178068258322L;
    String description = "I’m sending an email because I’m having a problem setting";
    List<String> tags = Arrays.asList("sample",
        "support",
        "zendesk");

    long auditId = 123;
    long auditTicketId = 1;
    String auditCreatedAt = "testAuditCreatedAt";
    long auditAuthorId = 456;
    List<Event> auditEvents = new ArrayList<>();
    Via auditVia = new Via("sample_ticket");
    Audit audit = new Audit(auditId, auditTicketId, auditCreatedAt, auditAuthorId, auditEvents,
        auditVia);

    String status = "open";
    String priority = "normal";

    Ticket ticket = new Ticket(url, id, subject, createdAt, updatedAt, type, requesterId,
        submitterId, assigneeId, description, tags, audit, auditVia, status, priority);

    List<Ticket> expectedTicketList = List.of(ticket);


    TicketsResult expected = new TicketsResult(expectedTicketList, "", "", 20);

    when(ticketService.tickets(1, 25)).thenReturn(Future.succeededFuture(expected));

    Future<TicketsResult> resultFuture = ticketController.tickets(1, 25);
    TicketsResult result = resultFuture.result();

    assertNotNull(result);
    assertFalse(result.tickets().isEmpty());
    assertEquals(1, result.tickets().size());
    assertEquals(expected.tickets().get(0), result.tickets().get(0));
    assertEquals(expected, result);
  }

  @Test
  void testNotFoundMessage() {
    String expected = "Resource could not be found";
    assertEquals(expected, ticketController.notFoundMessage());
  }
}