package com.github.throyer.granas.domain.user.persistence.models;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = RefreshToken.ENTITY)
@Table(name = RefreshToken.ENTITY)
public class RefreshToken {
  public static final String ENTITY = "refresh_token";

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "expires_at")
  private LocalDateTime expiresAt;

  @Column(name = "available")
  private Boolean available = true;

  @ManyToOne(fetch = EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  public RefreshToken(User user, Integer daysToExpire) {
    this.user = user;
    this.expiresAt = LocalDateTime.now().plusDays(daysToExpire);
    this.code = UUID.randomUUID().toString();
  }

  public Boolean isExpired() {
    return this.expiresAt.isBefore(LocalDateTime.now());
  }
}
