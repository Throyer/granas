package com.github.throyer.granas.domain.transaction.persistence.models;

import static com.github.throyer.granas.shared.sql.Queries.NON_DELETED_CLAUSE;
import static jakarta.persistence.GenerationType.IDENTITY;

import org.hibernate.annotations.SQLRestriction;

import com.github.throyer.granas.shared.persistence.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = Tag.ENTITY)
@Entity(name = Tag.ENTITY)
@SQLRestriction(NON_DELETED_CLAUSE)
@NoArgsConstructor
public class Tag extends Auditable {
  public static final String ENTITY = "tag";

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "name", nullable = true)
  private String name;
}