package com.github.throyer.granas.domain.authentication.controllers.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public interface UsernameOrPasswordInvalidResponse {
  @Schema(example = "Invalid password or username.", requiredMode = REQUIRED)
  String getMessage();

  @Schema(example = "403", requiredMode = REQUIRED)
  Integer getStatus();
}
