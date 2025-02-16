package com.github.throyer.granas.domain.user.persistence.models;

import static com.github.throyer.granas.shared.sql.Queries.NON_DELETED_CLAUSE;
import static jakarta.persistence.GenerationType.IDENTITY;

import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;

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
@NoArgsConstructor
@Entity(name = Role.ENTITY)
@Table(name = Role.ENTITY)
@SQLRestriction(NON_DELETED_CLAUSE)
public class Role extends Auditable implements GrantedAuthority {
  public static final String USER = "USER";
  public static final String ADM = "ADM";
  public static final String ENTITY = "role";

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "name", unique = true, length = 100)
  private String name;

  @Column(name = "deleted_name")
  private String deletedName;

  @Column(name = "short_name", unique = true)
  private String shortName;

  @Column(name = "deleted_short_name")
  private String deletedShortName;

  @Column(unique = true)
  private String description;

  @Column(name = "active", nullable = false)
  private Boolean active = true;

  @Override
  public String getAuthority() {
    return this.getShortName();
  }

  public Role(Long id, String shortName) {
    this.id = id;
    this.shortName = shortName;
  }
}
