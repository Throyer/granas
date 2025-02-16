package com.github.throyer.granas.shared.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Quote {
  private String movie;
  private String quote;

  @Override
  public String toString() {
    return movie;
  }

  public static Quote from(String movie, String quote) {
    return new Quote(movie, quote);
  }
}
