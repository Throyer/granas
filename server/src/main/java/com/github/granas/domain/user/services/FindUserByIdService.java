package com.github.granas.domain.user.services;

import static com.github.granas.shared.http.Responses.notFound;

import org.springframework.stereotype.Service;

import com.github.granas.domain.user.persistence.models.User;
import com.github.granas.domain.user.persistence.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FindUserByIdService {
  private final UserRepository repository;
  
  public User find(Long id) {
    return repository
      .findByIdFetchRoles(id)
        .orElseThrow(() -> notFound("Not found"));
  }
}
