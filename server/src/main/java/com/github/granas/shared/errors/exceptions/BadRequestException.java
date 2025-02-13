package com.github.granas.shared.errors.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.github.granas.shared.errors.ValidationError;

public class BadRequestException extends RuntimeException {
  List<ValidationError> errors;

  public BadRequestException() {
    this.errors = new ArrayList<>();
  }

  public BadRequestException(List<ValidationError> errors) {
    this.errors = errors;
  }

  public BadRequestException(String message, List<ValidationError> errors) {
    super(message);
    this.errors = errors;
  }

  public List<ValidationError> getErrors() {
    return errors;
  }

  public void add(ValidationError error) {
    this.errors.add(error);
  }

  public Boolean hasError() {
    return !this.errors.isEmpty();
  }
}
