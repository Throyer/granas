package com.github.throyer.granas.domain.transaction.persistence.models;

public enum Type {
  INCOME("Entrada"),
  OUTCOME("Saída");

  private final String description;

  private Type(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
