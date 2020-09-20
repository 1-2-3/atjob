package com.bzb.atjob.config.auth;

import com.bzb.atjob.common.auth.JwtToken;
import com.bzb.atjob.common.auth.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/** 自定义实现 ShiroRealm，包含认证和授权两大模块. */
public class ShiroRealm extends AuthorizingRealm {

  // @Autowired
  // private RedisService redisService;
  // @Autowired
  // private UserManager userManager;

  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof JwtToken;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    return simpleAuthorizationInfo;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {

    // 这里的 token是从 JwtFilter 的 executeLogin 方法传递过来的
    String token = authenticationToken.getCredentials().toString();

    String username = JwtUtil.getUsername(token);

    if (StringUtils.isBlank(username)) {
      throw new AuthenticationException("token校验不通过");
    }

    if (!JwtUtil.verify(token, username)) {
      throw new AuthenticationException("token校验不通过");
    }

    return new SimpleAuthenticationInfo(token, token, "atjob_shiro_realm");
  }
}
