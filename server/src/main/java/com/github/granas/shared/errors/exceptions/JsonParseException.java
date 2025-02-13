package com.github.granas.shared.errors.exceptions;

import lombok.Getter;

@Getter
public class JsonParseException extends RuntimeException {
  private final String json;
  public JsonParseException(String json, Throwable cause) {
    super(cause);
    this.json = json;
  }
}
