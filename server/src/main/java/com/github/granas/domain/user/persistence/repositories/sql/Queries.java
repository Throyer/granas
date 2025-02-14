package com.github.granas.domain.user.persistence.repositories.sql;

public class Queries {
  public Queries() { }
  
  //language=sql
  public static final String SELECT_IDS_FROM_USERS =
  """
  select u.id from user u      
  """;

  //language=sql
  public static final String SELECT_DISTINCT_USERS_FETCH_ROLES =
  """
  select distinct u from user u
  join fetch u.roles
  where u.id in (:user_ids) order by u.id
  """;

  //language=sql
  public static final String COUNT_IDS_FROM_USERS =
  """
  select count(u.id) from user u
  """;

    //language=sql
  public static final String DISABLE_OLD_REFRESH_TOKENS_FROM_USER = 
  """
  update
    refresh_token refresh
  set
    refresh.available = false
  where
    refresh.user.id = :user_id and refresh.available = true
  """;

  //language=sql
  public static final String FIND_REFRESH_TOKEN_BY_CODE_FETCH_USER_AND_ROLES = 
  """
  select
    refresh
  from
    refresh_token refresh
    join fetch refresh.user user
    join fetch user.roles
  where
    refresh.code = :code and refresh.available = true
  """;
}
