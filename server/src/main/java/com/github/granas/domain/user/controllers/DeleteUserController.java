package com.github.granas.domain.user.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.granas.domain.user.services.RemoveUserService;

import static com.github.granas.shared.security.Authorized.checkAuthorityOrThrowUnauthorized;
import static com.github.granas.utils.ID.decode;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@Tag(name = "Users")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class DeleteUserController {
  private final RemoveUserService service;
  
  @DeleteMapping("/{user_id}")
  @ResponseStatus(NO_CONTENT)
  @SecurityRequirement(name = "jwt")
  @PreAuthorize("hasAnyAuthority('USER')")
  @Operation(summary = "Delete user")
  public void destroy(@PathVariable("user_id") String hash) {
    var id = decode(hash);
    checkAuthorityOrThrowUnauthorized(session -> session.hasIdOrIsAdmin(id));
    service.remove(id);
  }
}
