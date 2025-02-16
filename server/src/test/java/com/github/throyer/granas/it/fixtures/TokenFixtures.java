package com.github.throyer.granas.it.fixtures;

import static com.github.throyer.granas.domain.utils.Random.between;
import static java.time.LocalDateTime.now;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.github.throyer.granas.shared.identity.IdentityEncoder;
import com.github.throyer.granas.shared.jwt.JWT;

@Component
public class TokenFixtures {
  private static JWT JWT;
  private static IdentityEncoder ID;

  public TokenFixtures(JWT jwt, IdentityEncoder identity) {
    TokenFixtures.JWT = jwt;
    TokenFixtures.ID = identity;
  }

  public static String token(Long id, String roles, String secret) {
    return token(id, now().plusHours(24), secret, List.of(roles.split(",")));
  }

  public static String token(String roles, String secret) {
    return token(com.github.throyer.granas.domain.utils.Random.between(1, 9999).longValue(), now().plusHours(24), secret, List.of(roles.split(",")));
  }

  public static String token(LocalDateTime expiration, String roles, String secret) {
    return token(between(1, 9999).longValue(), expiration, secret, List.of(roles.split(",")));
  }

  public static String token(Long id, LocalDateTime expiration, String secret, List<String> roles) {
    var token = JWT.encode(ID.encode(id), roles, expiration, secret);
    return String.format("Bearer %s", token);
  }
}
