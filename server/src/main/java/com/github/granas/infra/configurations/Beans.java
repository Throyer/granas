package com.github.granas.infra.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.granas.infra.environments.Security;

@Configuration
public class Beans {
  @Bean
  PasswordEncoder password(Security security) {
    return new BCryptPasswordEncoder(security.getHashLength());
  }
}
