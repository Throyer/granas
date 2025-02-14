package com.github.granas.domain.authentication.services;

import static com.github.granas.domain.authentication.i18n.Messages.EMAIL_WAS_NOT_CONFIRMED_MESSAGE;
import static com.github.granas.domain.authentication.i18n.Messages.INVALID_USERNAME_OR_PASSWORD_MESSAGE;
import static com.github.granas.shared.http.Responses.forbidden;
import static com.github.granas.utils.ID.encode;
import static java.time.LocalDateTime.now;

import org.springframework.stereotype.Service;

import com.github.granas.domain.authentication.dtos.Authentication;
import com.github.granas.domain.authentication.dtos.CreateAuthenticationWithEmailAndPassword;
import com.github.granas.domain.user.dtos.UserInformation;
import com.github.granas.domain.user.persistence.models.RefreshToken;
import com.github.granas.domain.user.persistence.repositories.RefreshTokenRepository;
import com.github.granas.domain.user.persistence.repositories.UserRepository;
import com.github.granas.infra.environments.Security;
import com.github.granas.shared.i18n.Internationalization;
import com.github.granas.shared.jwt.JWT;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class CreateAuthenticationWithEmailAndPasswordService {
  private final Internationalization i18n;
  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;
  private final Security security;
  private final JWT jwt;

  public Authentication create(CreateAuthenticationWithEmailAndPassword body) {
    var email = body.getEmail();
    var password = body.getPassword();
    log.info("creating new session with username and password.");
    
    var user = userRepository.findOptionalByEmail(email)
        .orElseThrow(() -> {
          log.info("could not create session, user not find.");
          return forbidden(i18n.message(INVALID_USERNAME_OR_PASSWORD_MESSAGE));
        });
    
    if (!user.passwordMatches(password)) {
      log.info("could not create session, invalid password.");
      throw forbidden(i18n.message(INVALID_USERNAME_OR_PASSWORD_MESSAGE));
    }
    
    if (!user.emailHasConfirmed()) {
      log.info("could not create session, email was not confirmed.");
      throw forbidden(i18n.message(EMAIL_WAS_NOT_CONFIRMED_MESSAGE));
    }
    
    var now = now();
    var expiresAt = now.plusHours(security.getTokenExpirationInHours());

    var accessToken = jwt.encode(
      encode(user.getId()),
      user.getAuthorities(),
      expiresAt,
      security.getTokenSecret()
    );

    log.info("creating new refresh-token for session.");
    
    refreshTokenRepository.disableOldRefreshTokensFromUser(user.getId());
    var refreshToken = new RefreshToken(user, security.getRefreshTokenExpirationInDays());    
    refreshTokenRepository.save(refreshToken);
    
    log.info("refresh-token successfully created.");

    var auth = new Authentication(
      new UserInformation(user),
      accessToken,
      refreshToken.getCode(),
      expiresAt
    );

    log.info("session successfully created.");
    return auth;
  }
}
