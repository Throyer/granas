package com.github.granas.domain.user.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.github.granas.shared.http.Responses.ok;
import static com.github.granas.shared.security.Authorized.checkAuthorityOrThrowUnauthorized;
import static com.github.granas.utils.ID.decode;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.granas.domain.user.dtos.UserInformation;
import com.github.granas.domain.user.services.FindUserByIdService;

@Slf4j
@Tag(name = "Users")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class FindUserByIdController {
  private final FindUserByIdService service;
  
  @GetMapping("/{user_id}")
  @SecurityRequirement(name = "jwt")
  @PreAuthorize("hasAnyAuthority('USER')")
  @Operation(summary = "Show user info")
  public ResponseEntity<UserInformation> show(@PathVariable("user_id") String hash) {
    var id = decode(hash);
    checkAuthorityOrThrowUnauthorized(session -> session.hasIdOrIsAdmin(id));
    var user = service.find(id);
    return ok(new UserInformation(user));
  }
}