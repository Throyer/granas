package com.github.granas.utils;

import org.springframework.stereotype.Component;

import com.github.granas.shared.identity.IdentityEncoder;

import static java.util.Objects.requireNonNull;

@Component
public class ID {
  private static IdentityEncoder encoder;
  public ID(IdentityEncoder encoder) {
    ID.encoder = encoder;
  }

  public static String encode(Long id) {
    return requireNonNull(encoder, "the identity-encoder is null")
      .encode(id);
  }

  public static Long decode(String id) {
    return requireNonNull(encoder, "idethe ntity-encoder is null")
      .decode(id);
  }
}