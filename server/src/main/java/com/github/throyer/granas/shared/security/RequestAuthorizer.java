package com.github.throyer.granas.shared.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RequestAuthorizer {
  void tryAuthorize(
    HttpServletRequest request,
    HttpServletResponse response
  );
}

