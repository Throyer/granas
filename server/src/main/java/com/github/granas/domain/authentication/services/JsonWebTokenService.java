package com.github.granas.domain.authentication.services;

import static io.jsonwebtoken.Jwts.builder;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.lang.String.join;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.ZoneId.systemDefault;
import static java.util.Date.from;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.github.granas.shared.jwt.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.WeakKeyException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class JsonWebTokenService implements JWT {
  private static final String ROLES_KEY_ON_JWT = "roles";

  @Override
  public String encode(String id, List<String> roles, LocalDateTime expiresAt, String secret) {
    log.info("creating new jwt, id: [{}], roles: [{}].", id, roles.toString());
    var accessToken = builder()
      .setSubject(id)
      .claim(ROLES_KEY_ON_JWT, join(",", roles))
      .setExpiration(from(expiresAt.atZone(systemDefault()).toInstant()))
      .signWith(secretToKey(secret))
      .compact();
    
    log.info("successfully created new jwt, id: [{}], roles: [{}].", id, roles);
    return accessToken;
  }

  @Override
  public <T> T decode(String token, String secret, BiFunction<String, List<String>, T> transformer) {
    log.info("decoding a jwt");
    var decoded = Jwts
      .parserBuilder()
      .setSigningKey(secretToKey(secret))
        .build()
          .parseClaimsJws(token);

    var id = decoded
      .getBody()
      .getSubject();

    var roles = List.of(
      decoded
      .getBody()
      .get(ROLES_KEY_ON_JWT)
        .toString()
        .split(",")
    );
       
    log.info("successfully decode a jwt, id: [{}], roles: [{}]", id, roles);
    
    return transformer.apply(id, roles);
  }

  private SecretKey secretToKey(String secret) {
    log.info("creating secret-key.");
    var bytes = secret.getBytes(UTF_8);    
    try {
      var key = hmacShaKeyFor(bytes);      
      log.info("secret-key successfully created.");
      return key;
    } catch (WeakKeyException exception) {
      log.warn("secret-key created, but it is a weak key, extremely not recommended in productive environments. details:\n{}", exception.getMessage());
      return hmacShaKeyFor(Arrays.copyOf(bytes, 64));
    }
  }
}
