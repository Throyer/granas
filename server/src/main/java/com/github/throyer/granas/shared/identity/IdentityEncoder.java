package com.github.throyer.granas.shared.identity;

public interface IdentityEncoder {
  String encode(Long id);
  Long decode(String id);
}
