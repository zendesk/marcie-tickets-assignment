package com.zendesk.marcie.data;

public enum Priority {
  URGENT,
  HIGH,
  NORMAL,
  LOW;

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }
}
