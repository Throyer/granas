package com.github.granas.domain.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserData {
  @Schema(example = "Jubileu da silva")
  @NotEmpty(message = "{field.name.required}")
  @Size(min = 1, max = 100, message = "{field.name.invalid-size}")
  private String name;

  @Schema(example = "jubileu@email.com")
  @NotEmpty(message = "{field.email.required}")
  @Email(message = "{field.email.invalid}")
  private String email;

  @Schema(example = "veryStrongAndSecurePassword")
  @NotEmpty(message = "{field.password.required}")
  @Size(min = 8, max = 155, message = "{field.password.invalid-size}")
  private String password;
}
