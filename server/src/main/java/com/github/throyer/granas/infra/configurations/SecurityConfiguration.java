package com.github.throyer.granas.infra.configurations;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.github.throyer.granas.infra.environments.SwaggerSecurity;
import com.github.throyer.granas.infra.middlewares.AuthenticationMiddleware;
import com.github.throyer.granas.shared.http.Responses;
import com.github.throyer.granas.shared.routes.Public;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfiguration {
  private final AuthenticationMiddleware middleware;
  private final PasswordEncoder encoder;
  private final SwaggerSecurity swaggerSecurity;
  private final Public publics;

  @Autowired
  protected void globalConfiguration(AuthenticationManagerBuilder authentication) throws Exception {
    if (swaggerSecurity.isEnabled()) {
      authentication
        .inMemoryAuthentication()
        .passwordEncoder(encoder)
        .withUser(swaggerSecurity.getUsername())
        .password(encoder.encode(swaggerSecurity.getPassword()))
        .authorities(List.of());
    }
  }

  @Bean
  public SecurityFilterChain api(HttpSecurity http) throws Exception {
    publics
      .add(GET, "/", "/docs", "/swagger-ui/**", "/v3/api-docs/**")
      .add(POST, "/users", "/authentication/**", "/recoveries/**")
      .inject(http);
  
    http
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(request -> request.anyRequest().authenticated())
      .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
      .addFilterBefore(middleware, UsernamePasswordAuthenticationFilter.class)
      .exceptionHandling(handler -> handler.authenticationEntryPoint(Responses::forbidden))
      .cors(configurer -> new CorsConfiguration().applyPermitDefaultValues());
    
    return http.build();
  }

  @Bean
  @Order(1)
  public SecurityFilterChain swagger(HttpSecurity http) throws Exception {
    if (swaggerSecurity.isEnabled()) {
      http
        .securityMatcher("/swagger-ui/**")
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
        .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
        .httpBasic(withDefaults());

        return http.build();   
    }
    
    http
      .securityMatcher("/swagger-ui/**")
      .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());    
    return http.build();
  }
}
