package com.zendesk.marcie.data;

import java.util.Collections;
import java.util.List;

/**
 * Represents a Post data model.
 */
@SuppressWarnings("UnusedVariable")
public record Post(int userId, int id, String title,
                   String body, List<Comment> comments) {

  public Post(int userId, int id, String title, String body) {
    this(userId, id, title, body, Collections.emptyList());
  }

  public Post withComments(List<Comment> comments) {
    return new Post(userId, id, title, body, comments);
  }

}
