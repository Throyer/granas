package com.github.granas.domain.authentication.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.granas.domain.authentication.dtos.Authentication;
import com.github.granas.domain.authentication.dtos.CreateAuthenticationWithEmailAndPassword;
import com.github.granas.domain.authentication.swagger.EmailNotConfirmedResponse;
import com.github.granas.domain.authentication.swagger.UsernameOrPasswordInvalidResponse;
import com.github.granas.infra.environments.Security;
import com.github.granas.shared.http.Responses;
import com.github.granas.shared.jwt.JWT;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Authentication")
@RequestMapping("/authentication")
public class CreateAuthenticationWithEmailAndPasswordController {
  private final JWT jwt;
  private final Security security;

  @PostMapping
  @ApiResponse(
    responseCode = "403",
    description = """
      Incorrect username or password,
      also happens when the `email` was not confirmed by the user.
    """,
    content = {
    @Content(schema = @Schema(oneOf = {
      UsernameOrPasswordInvalidResponse.class,
      EmailNotConfirmedResponse.class
    }))
  })
  @Operation(summary = "Create a jwt token")
  public ResponseEntity<Authentication> create(@RequestBody @Valid CreateAuthenticationWithEmailAndPassword body) {
    log.info("creating new session");

    var expiresAt = LocalDateTime.now().plusMinutes(30);
    var accessToken = jwt.encode("1", List.of("ADM"), expiresAt, security.getTokenSecret());

    var session = new Authentication(
      accessToken,
      null,
      null
    );

    log.info("session successfully created");
    return Responses.ok(session);
  }
}
