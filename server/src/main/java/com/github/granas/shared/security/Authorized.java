package com.github.granas.shared.security;

import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class Authorized extends User {
  private final Long id;

  public Authorized(Long id, List<SimpleGrantedAuthority> authorities) {
    super("USERNAME", "SECRET", authorities);
    this.id = id;
  }

  public UsernamePasswordAuthenticationToken getAuthentication() {
    return new UsernamePasswordAuthenticationToken(this, null, getAuthorities());
  }

  @Override
  public String toString() {
    return getId().toString();
  }

  public static Optional<Authorized> current() {
    var principal = ofNullable(
      getContext().getAuthentication())
      .map(Authentication::getPrincipal)
      .orElse(null);

    if (isNull(principal)) {
      return empty();
    }

    if (principal instanceof Authorized authorized) {
      return Optional.of(authorized);
    }

    return empty();
  }
}
