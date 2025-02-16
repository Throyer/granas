package com.github.throyer.granas.domain.authentication.services;

import static java.util.Objects.requireNonNull;
import static java.util.Arrays.stream;

import org.hashids.Hashids;
import org.springframework.stereotype.Service;

import com.github.throyer.granas.infra.environments.Security;
import com.github.throyer.granas.shared.errors.exceptions.IdentityEncoderException;
import com.github.throyer.granas.shared.identity.IdentityEncoder;

@Service
public class IdentityEncoderService implements IdentityEncoder {
  private final Hashids hashids;

  public IdentityEncoderService(Security security) {
    this.hashids = new Hashids(security.getHashidSecret(), security.getHashLength());
  }

  @Override
  public String encode(Long id) {
    try {
      return this.hashids.encode(requireNonNull(id, "id is null"));
    } catch (Exception exception) {
      throw new IdentityEncoderException("error on id encoding", exception);
    }
  }

  @Override
  public Long decode(String id) {
    try {
      return stream(this.hashids.decode(requireNonNull(id, "id is null")))
        .boxed()
        .findFirst()
        .orElse(null);
    } catch (Exception exception) {
      throw new IdentityEncoderException("error on id encoding", exception);
    }
  }
}
