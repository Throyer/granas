package com.github.throyer.granas.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class Passwords {
  private static PasswordEncoder encoder;

  public Passwords(PasswordEncoder encoder) {
    Passwords.encoder = encoder;
  }

  public static String encode(String password) {
    return requireNonNull(encoder, "password encoder is null")
      .encode(password);
  }

  public static Boolean matches(String raw, String password) {
    return requireNonNull(encoder, "password encoder is null")
      .matches(raw, password);
  }
}
