package com.github.throyer.granas.shared.jwt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiFunction;

public interface JWT {
  String encode(
    String id,
    List<String> roles,
    LocalDateTime expiresAt,
    String secret
  );

  <T> T decode(
    String token,
    String secret,
    BiFunction<String, List<String>, T> transformer
  );
}