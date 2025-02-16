package com.github.throyer.granas.domain.user.persistence.repositories;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.github.throyer.granas.domain.user.persistence.models.User;
import com.github.throyer.granas.shared.pagination.Page;

public interface UserRepository {
  Page<User> findAllFetchRoles(Pageable pageable);
  Optional<User> findOptionalByEmail(String email);
  Boolean existsByEmail(String email);
  Optional<User> findByIdFetchRoles(Long id);  
  void save(User user);
}
