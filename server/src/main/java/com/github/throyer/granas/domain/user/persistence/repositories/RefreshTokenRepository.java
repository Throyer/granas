package com.github.throyer.granas.domain.user.persistence.repositories;

import static com.github.throyer.granas.domain.user.persistence.repositories.sql.Queries.DISABLE_OLD_REFRESH_TOKENS_FROM_USER;
import static com.github.throyer.granas.domain.user.persistence.repositories.sql.Queries.FIND_REFRESH_TOKEN_BY_CODE_FETCH_USER_AND_ROLES;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.github.throyer.granas.domain.user.persistence.models.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  @Modifying
  @Transactional
  @Query(value = DISABLE_OLD_REFRESH_TOKENS_FROM_USER)
  void disableOldRefreshTokensFromUser(@Param("user_id") Long userId);

  @Query(FIND_REFRESH_TOKEN_BY_CODE_FETCH_USER_AND_ROLES)
  Optional<RefreshToken> findOptionalByCodeAndAvailableIsTrue(@Param("code") String code);
}