package com.github.throyer.granas.shared.persistence;

import static com.github.throyer.granas.utils.ID.decode;
import static com.github.throyer.granas.utils.ID.encode;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {
  public abstract Long getId();
  public abstract void setId(Long id);

  public void setId(String id) {
    this.setId(decode(id));
  }

  public String getStringId() {
    return encode(this.getId());
  }
}
