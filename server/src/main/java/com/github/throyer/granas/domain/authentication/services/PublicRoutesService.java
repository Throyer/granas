package com.github.throyer.granas.domain.authentication.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import com.github.throyer.granas.shared.routes.Public;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class PublicRoutesService implements Public {  
  private final Map<HttpMethod, String[]> routes = new HashMap<>();
  private final List<AntPathRequestMatcher> matchers = new ArrayList<>();

  @Override
  public Public add(HttpMethod method, String... routes) {
    this.routes.put(method, routes);

    Stream.of(routes)
      .forEach(route -> this.matchers.
        add(new AntPathRequestMatcher(route, method.name())));

    return this;
  }

  @Override
  public boolean anyMatch(HttpServletRequest request) {
    try {
      return this.matchers.stream().anyMatch(requestMatcher -> requestMatcher.matches(request));
    } catch (Exception exception) {
      log.error("error on route matching", exception);
      return false;
    }
  }

  @Override
  public void inject(HttpSecurity http) {
    this.routes.forEach((method, routes) -> {
      try {
        http
        .securityMatcher("/**")
        .authorizeHttpRequests(authorization -> authorization
          .requestMatchers(method, routes)
            .permitAll());
      } catch (Exception exception) {
        log.error("error on set public routes", exception);
      }
    });
  }
}
