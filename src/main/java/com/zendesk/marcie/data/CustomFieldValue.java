package com.zendesk.marcie.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomFieldValue {


  private Long id;

  @JsonFormat(
      with = {
          JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
          JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED
      })
  private String[] value;

  public CustomFieldValue(Long id, String[] value) {
    this.id = id;
    this.value = value;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String[] getValue() {
    return value;
  }

  public void setValue(String[] value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "CustomFieldValue{" + "id=" + id + ", value=" + Arrays.toString(value) + '}';
  }
}
