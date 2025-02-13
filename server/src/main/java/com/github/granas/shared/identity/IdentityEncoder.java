package com.github.granas.shared.identity;

public interface IdentityEncoder {
  String encode(Long id);
  Long decode(String id);
}
