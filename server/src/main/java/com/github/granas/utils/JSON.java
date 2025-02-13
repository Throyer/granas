package com.github.granas.utils;


import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.granas.shared.errors.exceptions.JsonParseException;

public class JSON {

  private JSON() { }

  public static final ObjectMapper MAPPER = new ObjectMapper()
    .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
    .configure(WRITE_DATES_AS_TIMESTAMPS, false)
    .findAndRegisterModules();

  public static <T> String stringify(final T object) {
    try {
      return MAPPER.writer().writeValueAsString(object);
    } catch (JsonProcessingException exception) {
      return "";
    }
  }

  public static <T> T parse(String json, Class<T> type) {
    try {
      var mapper = new ObjectMapper();
      mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
      return mapper.readValue(json, type);
    } catch (Exception exception) {
      throw new JsonParseException(json, exception);
    }
  }

  public static <T> T parse(String json, TypeReference<T> type) {
    try {
      var mapper = new ObjectMapper();
      mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
      return mapper.readValue(json, type);
    } catch (Exception exception) {
      throw new JsonParseException(json, exception);
    }
  }
}

