package com.github.granas.domain.user.services;

import static com.github.granas.shared.http.Responses.notFound;
import static com.github.granas.shared.security.Authorized.current;
import static java.time.LocalDateTime.now;

import org.springframework.stereotype.Service;

import com.github.granas.domain.user.persistence.models.User;
import com.github.granas.domain.user.persistence.repositories.UserRepository;
import com.github.granas.shared.security.Authorized;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RemoveUserService {
  private final UserRepository repository;

  public void remove(Long id) {    
    var user = repository
      .findByIdFetchRoles(id)
        .orElseThrow(() -> notFound("User not found"));

    user.setDeletedEmail(user.getEmail());
    user.setDeletedAt(now());
    user.setDeletedBy(new User(current().map(Authorized::getId).orElse(null)));
    user.setEmail(null);
    
    repository.save(user);
  }
}