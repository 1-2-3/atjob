package com.bzb.atjob.common.auth;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/** JSON Web Token. */
@Data
public class JwtToken implements AuthenticationToken {

  private static final long serialVersionUID = 1286967166821748738L;

  private String token;

  private String exipreAt;

  public JwtToken(String token) {
    this.token = token;
  }

  public JwtToken(String token, String exipreAt) {
    this.token = token;
    this.exipreAt = exipreAt;
  }

  @Override
  public Object getPrincipal() {
    return token;
  }

  @Override
  public Object getCredentials() {
    return token;
  }
}
