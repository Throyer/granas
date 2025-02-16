package com.github.granas.domain.user.controllers.swagger;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import com.github.granas.domain.user.dtos.UserInformation;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

public interface UserPageResponse {
  @ArraySchema(schema = @Schema(implementation = UserInformation.class))
  List<UserInformation> getContent();

  @Schema(example = "0", requiredMode = REQUIRED)
  Integer getPage();

  @Schema(example = "10", requiredMode = REQUIRED)
  Integer getSize();

  @Schema(example = "128", requiredMode = REQUIRED)
  Integer getTotalPages();

  @Schema(example = "412", requiredMode = REQUIRED)
  Long getTotalElements();
}