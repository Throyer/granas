package com.github.granas.shared.security;

import static com.github.granas.shared.http.Responses.unauthorized;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.github.granas.domain.user.persistence.models.Role;

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

  public Boolean hasId(Long id) {
    return this.id.equals(id);
  }

  public Boolean hasAuthority(String authority) {
    return this.getAuthorities().stream()
      .anyMatch(role -> role.getAuthority().equalsIgnoreCase(authority));
  }

  public Boolean hasIdOrIsAdmin(Long id) {
    if (hasAuthority(Role.ADM)) {
      return true;
    }
    
    return hasId(id);
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

  public static void isPresent(Consumer<Authorized> consumer) {
    current().ifPresent(consumer);
  }

  public static void checkAuthorityOrThrowUnauthorized(Function<Authorized, Boolean> check) {
    var authorized = current()
      .orElse(null);

    if (isNull(authorized)) {
      return;
    }

    if (!check.apply(authorized)) {
      throw unauthorized("Not authorized.");
    }
  }
}
