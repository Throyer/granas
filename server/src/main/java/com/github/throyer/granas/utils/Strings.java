package com.github.throyer.granas.utils;

import static java.util.Objects.isNull;

import java.util.stream.Stream;

public class Strings {
  private Strings() { }

  public static Boolean isNullOrBlank(String text) {
    if (isNull(text)) {
      return true;
    }
    return text.isBlank();
  }

  public static Boolean anyOfThenIsNullOrEmpty(String... candidates) {
    return Stream.of(candidates).anyMatch(Strings::isNullOrBlank);
  }
}
