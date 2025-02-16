package com.github.throyer.granas.domain.user.controllers;

import static com.github.throyer.granas.shared.http.Responses.ok;
import static com.github.throyer.granas.shared.security.Authorized.checkAuthorityOrThrowUnauthorized;
import static com.github.throyer.granas.utils.ID.decode;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.throyer.granas.domain.user.dtos.UpdateUserData;
import com.github.throyer.granas.domain.user.dtos.UserInformation;
import com.github.throyer.granas.domain.user.services.UpdateUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Users")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UpdateUserController {
  private final UpdateUserService service;
  
  @PutMapping("/{user_id}")
  @SecurityRequirement(name = "jwt")
  @PreAuthorize("hasAnyAuthority('USER')")
  @Operation(summary = "Update user data")
  public ResponseEntity<UserInformation> update(
    @PathVariable("user_id") String hash,
    @RequestBody @Validated UpdateUserData data
  ) {
    var id = decode(hash);
    checkAuthorityOrThrowUnauthorized(session -> session.hasIdOrIsAdmin(id));
    var user = service.update(id, data);
    return ok(new UserInformation(user));
  }
}