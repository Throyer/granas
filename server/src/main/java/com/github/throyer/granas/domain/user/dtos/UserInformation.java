package com.github.throyer.granas.domain.user.dtos;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import com.github.throyer.granas.domain.user.persistence.models.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserInformation {
  @Schema(example = "BVnl07r1Joz3", requiredMode = REQUIRED)
  private final String id;

  @Schema(example = "Jubileu da Silva", requiredMode = REQUIRED)
  private final String name;

  @Schema(example = "jubileu@email.com", requiredMode = REQUIRED)
  private final String email;

  @Schema(example = "true", requiredMode = REQUIRED)
  private final Boolean active;

  @Schema(example = "[\"ADM\"]", requiredMode = REQUIRED)
  private final List<String> roles;

  public UserInformation(User user) {
    this.id = user.getStringId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.active = user.isActive();
    this.roles = user.getAuthorities();
  }
}
