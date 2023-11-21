package com.zendesk.marcie.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Represents a TicketList data model.
 */
@SuppressWarnings("UnusedVariable")
@JsonIgnoreProperties(ignoreUnknown = true)
public record TicketsResult(List<Ticket> tickets, String next_page, String previous_page, long count) {}