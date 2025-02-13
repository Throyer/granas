package com.github.granas.infra.handlers.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public interface TokenExpiredInvalidResponse {
  @Schema(example = "Token expired or invalid.", requiredMode = REQUIRED)
  String getMessage();

  @Schema(example = "403", requiredMode = REQUIRED)
  Integer getStatus();
}