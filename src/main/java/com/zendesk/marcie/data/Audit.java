package com.zendesk.marcie.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Represents a Audit data model.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Audit(long id, long ticket_id, String created_at, long author_id, List<Event> events,
                    Via via) {

}
