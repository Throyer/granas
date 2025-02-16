package com.github.throyer.granas.domain.transaction.persistence.models;

import static com.github.throyer.granas.shared.sql.Queries.NON_DELETED_CLAUSE;
import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import com.github.throyer.granas.shared.persistence.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = Transaction.ENTITY)
@Table(name = Transaction.ENTITY)
@SQLRestriction(NON_DELETED_CLAUSE)
public class Transaction extends Auditable {
  public static final String ENTITY = "transaction";

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "description", nullable = true)
  private String description;

  @Column(name = "amount", precision =10, scale = 2, nullable = true)
  private BigDecimal amount;

  @Column(name = "at", nullable = true)
  private LocalDateTime at;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private Type type;

  @ManyToMany(cascade = DETACH, fetch = LAZY)
  @JoinTable(name = "transaction_tag", 
    joinColumns = {@JoinColumn(name = "transaction_id")},
    inverseJoinColumns = {@JoinColumn(name = "tag_id")}
  )
  private List<Tag> tags;

  @CreationTimestamp
  @Column(name = "created_at", nullable = true)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = true)
  private LocalDateTime updatedAt;
}