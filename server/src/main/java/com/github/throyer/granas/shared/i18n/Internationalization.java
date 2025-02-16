package com.github.throyer.granas.shared.i18n;

public interface Internationalization {
  String message(String key);
  String message(String key, Object... args);
}
