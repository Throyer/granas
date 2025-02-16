package com.github.throyer.granas.infra.handlers.swagger;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

import com.github.throyer.granas.shared.errors.ValidationError;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public interface BadRequestResponse {
  @Schema(example = "Check the 'errors' property for more details.", requiredMode = REQUIRED)
  String getMessage();

  @Schema(example = "400", requiredMode = REQUIRED)
  Integer getStatus();

  @ArraySchema(schema = @Schema(implementation = ApiErrorBadRequestError.class))
  List<ValidationError> getErrors();

  interface ApiErrorBadRequestError {
    @Schema(example = "name", requiredMode = REQUIRED)
    String getField();

    @Schema(example = "field name is required", requiredMode = REQUIRED)
    String getMessage();
  }
}
