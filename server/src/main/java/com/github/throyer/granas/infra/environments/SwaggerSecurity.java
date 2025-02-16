package com.github.throyer.granas.infra.environments;

import static lombok.AccessLevel.NONE;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "swagger-security")
public class SwaggerSecurity {
  @Getter(value = NONE)
  private Boolean enabled;
  private String username;
  private String password;
  
  public Boolean isEnabled() {
    return this.enabled;
  }
}
