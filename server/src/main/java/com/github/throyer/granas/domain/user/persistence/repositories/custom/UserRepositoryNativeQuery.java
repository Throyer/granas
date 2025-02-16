package com.github.throyer.granas.domain.user.persistence.repositories.custom;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.throyer.granas.domain.user.persistence.models.User;
import com.github.throyer.granas.domain.user.persistence.repositories.sql.Queries;
import com.github.throyer.granas.shared.pagination.Page;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class UserRepositoryNativeQuery {
  private final EntityManager manager;

  public Page<User> findAllFetchRoles(Pageable pageable) {
    var range = manager
      .createQuery(Queries.SELECT_IDS_FROM_USERS, Long.class);

    var query = manager
      .createQuery(Queries.SELECT_DISTINCT_USERS_FETCH_ROLES, User.class);

    var count = manager
      .createQuery(Queries.COUNT_IDS_FROM_USERS, Long.class).getSingleResult();

    var pageNumber = pageable.getPageNumber();
    var pageSize = pageable.getPageSize();

    range.setFirstResult(pageNumber * pageSize);
    range.setMaxResults(pageSize);

    List<Long> ids = range.getResultList();

    query.setParameter("user_ids", ids);

    List<User> content = query.getResultList();

    return Page.of(content, pageNumber, pageSize, count);
  }
}
