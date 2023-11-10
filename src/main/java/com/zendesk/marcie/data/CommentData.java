package com.zendesk.marcie.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Represents a CommentList data model.
 */
@SuppressWarnings("UnusedVariable")
@JsonIgnoreProperties(ignoreUnknown = true)
public record CommentData(List<Comment> comments) {}