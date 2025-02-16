package com.github.throyer.granas.domain.user.persistence.repositories.springdata;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.throyer.granas.domain.user.persistence.models.User;

public interface UserRepositoryJpa extends JpaRepository<User, Long> {
  @EntityGraph(attributePaths = "roles", type = FETCH)
  Optional<User> findOptionalByEmail(String email);

  @EntityGraph(attributePaths = "roles", type = FETCH)
  Optional<User> findOptionalById(Long id);

  Boolean existsByEmail(String email);
}
