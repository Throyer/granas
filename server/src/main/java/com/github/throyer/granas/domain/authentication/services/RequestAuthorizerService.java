package com.github.throyer.granas.domain.authentication.services;

import static com.github.throyer.granas.shared.http.Responses.expired;
import static com.github.throyer.granas.utils.Authorization.extract;
import static java.util.Objects.isNull;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.github.throyer.granas.infra.environments.Security;
import com.github.throyer.granas.shared.identity.IdentityEncoder;
import com.github.throyer.granas.shared.jwt.JWT;
import com.github.throyer.granas.shared.routes.Public;
import com.github.throyer.granas.shared.security.Authorized;
import com.github.throyer.granas.shared.security.RequestAuthorizer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RequestAuthorizerService implements RequestAuthorizer {
  private final JWT jwt;
  private final Security security;
  private final Public publics;
  private final IdentityEncoder identity;

  @Override
  public void tryAuthorize(HttpServletRequest request, HttpServletResponse response) {
    if (publics.anyMatch(request)) {
      return;
    }

    var token = extract(request);

    if (isNull(token)) {
      return;
    }

    try {
      var authorized = jwt.decode(token, security.getTokenSecret(), this::authorized);
      
      SecurityContextHolder
      .getContext()
        .setAuthentication(authorized.getAuthentication());
    } catch (Exception exception) {
      expired(response);
    }
  }

  public Authorized authorized(String hash, List<String> roles) {
    var id = identity.decode(hash);
    var authorities = roles.stream()
      .map(SimpleGrantedAuthority::new)
        .toList();

    return new Authorized(id, authorities);
  }
}
