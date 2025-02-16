package com.github.throyer.granas.shared.routes;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import jakarta.servlet.http.HttpServletRequest;

public interface Public {
  Public add(HttpMethod method, String... routes);
  boolean anyMatch(HttpServletRequest request);
  void inject(HttpSecurity http);
}
