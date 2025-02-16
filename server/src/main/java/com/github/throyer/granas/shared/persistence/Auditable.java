package com.github.throyer.granas.shared.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.github.throyer.granas.domain.user.persistence.models.User;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@MappedSuperclass
public abstract class Auditable extends BaseEntity {
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @JoinColumn(name = "created_by")
  @ManyToOne(fetch = LAZY)
  private User createdBy;

  @JoinColumn(name = "updated_by")
  @ManyToOne(fetch = LAZY)
  private User updatedBy;

  @JoinColumn(name = "deleted_by")
  @ManyToOne(fetch = LAZY)
  private User deletedBy;
}