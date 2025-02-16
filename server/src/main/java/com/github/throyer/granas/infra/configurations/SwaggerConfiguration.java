package com.github.throyer.granas.infra.configurations;

import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;
import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfiguration {
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
    .info(new Info()
      .title("Granas API")
      .description("A simple JWT project")
      .version("v1.0.0")
    .license(new License()
      .name("GNU General Public License v3.0"))
    .contact(new Contact()
      .email("throyer.dev@gmail.com")
      .name("Renato Henrique")
      .url("https://github.com/Throyer")))
    .servers(List.of(
      new Server().url("http://localhost:8080")
    ))
    .components(new Components()
      .addSecuritySchemes("jwt", new SecurityScheme()
      .bearerFormat("JWT")
      .scheme("bearer")
      .type(HTTP)
      .in(HEADER)));
  }
}

