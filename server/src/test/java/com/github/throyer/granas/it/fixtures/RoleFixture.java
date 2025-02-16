package com.github.throyer.granas.it.fixtures;

import java.util.List;

import com.github.throyer.granas.domain.user.persistence.models.Role;


public class RoleFixture {
  public static List<Role> roles() {
    return List.of(
      new Role(1L, Role.USER),
      new Role(2L, Role.ADM)
    );
  }
}