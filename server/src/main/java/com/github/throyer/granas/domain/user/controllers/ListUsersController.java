package com.github.throyer.granas.domain.user.controllers;

import static com.github.throyer.granas.shared.http.Responses.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.throyer.granas.domain.user.controllers.swagger.UserPageResponse;
import com.github.throyer.granas.domain.user.dtos.UserInformation;
import com.github.throyer.granas.domain.user.services.FindAllUsersService;
import com.github.throyer.granas.infra.handlers.swagger.BadRequestResponse;
import com.github.throyer.granas.shared.pagination.Page;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Tag(name = "Users")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class ListUsersController {
  private final FindAllUsersService service;

  @GetMapping
  @SecurityRequirement(name = "jwt")
  @PreAuthorize("hasAnyAuthority('ADM')")
  @Operation(summary = "Returns a list of users")
  @ApiResponse(responseCode = "400", description = "Bad Request", content = {
    @Content(schema = @Schema(implementation = BadRequestResponse.class))
  })
  @ApiResponse(responseCode = "200", description = "Ok", content = {
    @Content(schema = @Schema(implementation = UserPageResponse.class))
  })
  public ResponseEntity<Page<UserInformation>> index(
    @Parameter(example = "0")
    @RequestParam(name = "page", required = false)
    Integer page,

    @Parameter(example = "10")
    @RequestParam(name = "size", required = false)
    Integer size
  ) {
    log.info("listing users.");
    var response = service.find(page, size);
    return ok(response.map(UserInformation::new));
  }
}
