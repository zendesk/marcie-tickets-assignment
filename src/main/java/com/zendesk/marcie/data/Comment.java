package com.zendesk.marcie.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a Comment data model.
 */
@SuppressWarnings("UnusedVariable")
@JsonIgnoreProperties(ignoreUnknown = true)
public record Comment(long id, String type, long author_id, String body, Via via,
                      String created_at) {

}
