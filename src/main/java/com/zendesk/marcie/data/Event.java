package com.zendesk.marcie.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Represents a Event data model.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Event(long id, String type, long author_id, String body, String html_body,
                    String plain_body, boolean event_public, long audit_id) {

}
