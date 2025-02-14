package com.github.granas.infra.constants;

public class SecurityConstants {
  public static final String SECURITY_TYPE = "Bearer";
  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String ACCEPTABLE_TOKEN_TYPE = SECURITY_TYPE + " ";
  public static final String CAN_T_WRITE_RESPONSE_ERROR = "can't write response error.";
  public static final Integer BEARER_WORD_LENGTH = SECURITY_TYPE.length();
}
