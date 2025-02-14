package com.github.granas.shared.sql;

public class Queries {
  private Queries() {}

  public static final String NON_DELETED_CLAUSE = "deleted_at IS NULL";
}
