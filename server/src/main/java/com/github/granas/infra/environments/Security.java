package com.github.granas.infra.environments;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security")
public class Security {
  private Integer hashLength;
  private String tokenSecret;
  private String hashidSecret;
  private Integer tokenExpirationInHours;
  private Integer refreshTokenExpirationInDays;
}
