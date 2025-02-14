package com.github.granas.domain.user.services;

import static com.github.granas.domain.user.i18n.Messages.EMAIL_UNAVAILABLE_MESSAGE;
import static com.github.granas.domain.user.persistence.models.Role.USER;
import static com.github.granas.shared.http.Responses.conflict;
import static com.github.granas.shared.http.Responses.internalServerError;

import org.springframework.stereotype.Service;

import com.github.granas.domain.user.dtos.CreateUserData;
import com.github.granas.domain.user.persistence.models.User;
import com.github.granas.domain.user.persistence.repositories.RoleRepository;
import com.github.granas.domain.user.persistence.repositories.UserRepository;
import com.github.granas.shared.i18n.Internationalization;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class CreateUserService {
  private final Internationalization i18n;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  public User create(CreateUserData data) {
    var name = data.getName();
    var email = data.getEmail();
    var password = data.getPassword();

    if (userRepository.existsByEmail(email)) {
      log.warn("could not create user, e-mail is unavailable.");
      throw conflict(i18n.message(EMAIL_UNAVAILABLE_MESSAGE));
    }

    var role = roleRepository.findOptionalByShortName(USER)
      .orElseThrow(() -> internalServerError("role user not found"));

    var user = new User(
      name,
      email,
      password,
      role
    );

    userRepository.save(user);

    log.info("new user successfully created.");

    return user;
  }
}
