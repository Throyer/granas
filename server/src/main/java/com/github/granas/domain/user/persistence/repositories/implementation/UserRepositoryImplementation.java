package com.github.granas.domain.user.persistence.repositories.implementation;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.granas.domain.user.persistence.models.User;
import com.github.granas.domain.user.persistence.repositories.UserRepository;
import com.github.granas.domain.user.persistence.repositories.custom.UserRepositoryNativeQuery;
import com.github.granas.domain.user.persistence.repositories.springdata.UserRepositoryJpa;
import com.github.granas.shared.pagination.Page;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class UserRepositoryImplementation implements UserRepository {
  private final UserRepositoryNativeQuery sql;
  private final UserRepositoryJpa jpa;

  @Override
  public Page<User> findAllFetchRoles(Pageable pageable) {
    return sql.findAllFetchRoles(pageable);
  }

  @Override
  public Optional<User> findOptionalByEmail(String email) {
    return jpa.findOptionalByEmail(email);
  }

  @Override
  public Boolean existsByEmail(String email) {
    return jpa.existsByEmail(email);
  }

  @Override
  public void save(User user) {
    jpa.save(user);
  }
  
  @Override
  public Optional<User> findByIdFetchRoles(Long id) {
    return jpa.findOptionalById(id);
  }
}
