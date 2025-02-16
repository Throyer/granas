package com.github.granas.domain.user.services;


import static com.github.granas.shared.http.Responses.notFound;
import static com.github.granas.shared.security.Authorized.current;
import static java.time.LocalDateTime.now;

import org.springframework.stereotype.Service;

import com.github.granas.domain.user.dtos.UpdateUserData;
import com.github.granas.domain.user.persistence.models.User;
import com.github.granas.domain.user.persistence.repositories.UserRepository;
import com.github.granas.shared.security.Authorized;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UpdateUserService {
  private final UserRepository repository;

  public User update(Long id, UpdateUserData data) {
    var actual = repository
      .findByIdFetchRoles(id)
        .orElseThrow(() -> notFound("User not found"));
        
    actual.setName(data.getName());

    actual.setUpdatedBy(new User(current().map(Authorized::getId).orElse(null)));
    actual.setUpdatedAt(now());
    repository.save(actual);

    return actual;
  }
}
