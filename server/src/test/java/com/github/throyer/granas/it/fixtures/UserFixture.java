package com.github.throyer.granas.it.fixtures;

import static com.github.throyer.granas.domain.utils.Random.between;
import static com.github.throyer.granas.domain.utils.Random.element;
import static com.github.throyer.granas.domain.utils.Random.faker;
import static com.github.throyer.granas.it.fixtures.RoleFixture.roles;

import java.util.ArrayList;
import java.util.List;

import com.github.throyer.granas.domain.user.persistence.models.Role;
import com.github.throyer.granas.domain.user.persistence.models.User;



public class UserFixture {
  public static Long id() {
    return between(1, 999).longValue();
  }

  public static String name() {
    return faker().name().fullName();
  }

  public static String email() {
    return faker().internet().safeEmailAddress();
  }

  public static String password() {
    return faker().regexify("[a-z]{5,13}[1-9]{1,5}[A-Z]{1,5}[#?!@$%^&*-]{1,5}");
  }

  public static List<User> users(Integer size) {
    List<User> users = new ArrayList<>();
    for (int index = 0; index < size; index++) {
      users.add(user());
    }
    return users;
  }

  public static User user() {
    return user(List.of(element(roles())));
  }

  public static User user(Boolean emailConfirmed) {
    return new User(id(), name(), email(), password(), emailConfirmed, List.of(element(roles())));
  }

  public static User user(List<Role> roles) {
    return new User(id(), name(), email(), password(), true, roles);
  }

  public static User user(Long id, String name, String email, String password, List<Role> roles) {
    return new User(id, name, email, password, true, roles);
  }
}
