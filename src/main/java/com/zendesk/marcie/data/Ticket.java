package com.zendesk.marcie.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.List;

/**
 * Represents a Tickets data model.
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public record Ticket(String url, int id, String subject, Date created_at, Date updated_at,
                     String type, long requester_id, long submitter_id, long assignee_id,
                     String description, List<String> tags, Audit audit, Via via, String status,
                     String priority) {

}
