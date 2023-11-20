package com.zendesk.marcie.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a TicketList data model.
 */
@SuppressWarnings("UnusedVariable")
@JsonIgnoreProperties(ignoreUnknown = true)
public record TicketResult(Ticket ticket) {}
