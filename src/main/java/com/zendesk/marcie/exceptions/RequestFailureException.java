package com.zendesk.marcie.exceptions;

public class RequestFailureException extends RuntimeException {

  private final int statusCode;

  public RequestFailureException(int statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
