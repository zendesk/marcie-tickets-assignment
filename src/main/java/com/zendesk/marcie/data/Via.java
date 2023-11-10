package com.zendesk.marcie.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a Via data model.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Via(String channel) {

}
