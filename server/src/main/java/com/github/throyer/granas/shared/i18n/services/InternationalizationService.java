package com.github.throyer.granas.shared.i18n.services;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import com.github.throyer.granas.shared.i18n.Internationalization;

import lombok.AllArgsConstructor;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

@Service
@AllArgsConstructor
public class InternationalizationService implements Internationalization {
  private final ResourceBundleMessageSource messageSource;

  @Override
  public String message(String key) {
    return messageSource.getMessage(key, null, getLocale());
  }

  @Override
  public String message(String key, Object... args) {
    return messageSource.getMessage(key, args, getLocale());
  }
}
