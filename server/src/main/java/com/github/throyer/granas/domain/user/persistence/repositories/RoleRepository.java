package com.github.throyer.granas.domain.user.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.throyer.granas.domain.user.persistence.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
  List<Role> findByShortNameIn(List<String> names);
  
  Optional<Role> findOptionalByShortName(String shortName);

  Optional<Role> findOptionalByName(String name);

  Boolean existsByShortName(String shortName);

  Boolean existsByName(String name);
}
