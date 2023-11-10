package com.zendesk.marcie.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a Ticket Comment data model.
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public record TicketComment(long id, String type, long author_id, String body) {

}
