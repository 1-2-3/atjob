package com.bzb.atjob.common.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher; 

public class JwtFilter extends BasicHttpAuthenticationFilter {

  private static final String TOKEN = "authorization";
  private AntPathMatcher pathMatcher = new AntPathMatcher();

  @Override
  protected boolean isAccessAllowed(
      ServletRequest request, ServletResponse response, Object mappedValue)
      throws UnauthorizedException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;

    // 跳过不需要身份认证的 url
    String[] anonUrl = {
      "/api/v1/auth/login",
      "/webjars/**",
      "/swagger-ui.html/**",
      "/swagger-resources/**",
      "/v2/api-docs"
    };

    for (String u : anonUrl) {
      if (pathMatcher.match(u, httpServletRequest.getRequestURI())) {
        return true;
      }
    }

    if (isLoginAttempt(request, response)) {
      return executeLogin(request, response);
    } else {
      return false;
    }
  }

  @Override
  protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
    HttpServletRequest req = (HttpServletRequest) request;
    String token = req.getHeader(TOKEN);
    return token != null;
  }

  @Override
  protected boolean executeLogin(ServletRequest request, ServletResponse response) {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String token = httpServletRequest.getHeader(TOKEN);
    JwtToken jwtToken = new JwtToken(token);
    try {
      getSubject(request, response).login(jwtToken);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /** 对跨域提供支持. */
  // @Override
  // protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception
  // {
  //   HttpServletRequest httpServletRequest = (HttpServletRequest) request;
  //   HttpServletResponse httpServletResponse = (HttpServletResponse) response;
  //   httpServletResponse.setHeader(
  //       "Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
  //   httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
  //   httpServletResponse.setHeader(
  //       "Access-Control-Allow-Headers",
  //       httpServletRequest.getHeader("Access-Control-Request-Headers"));
  //   // 跨域时会首先发送一个 option请求，这里我们给 option请求直接返回正常状态
  //   if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
  //     httpServletResponse.setStatus(HttpStatus.OK.value());
  //     return false;
  //   }
  //   return super.preHandle(request, response);
  // }

  @Override
  protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
    HttpServletResponse httpResponse = WebUtils.toHttp(response);
    httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
    httpResponse.setCharacterEncoding("utf-8");
    httpResponse.setContentType("application/json; charset=utf-8");
    return false;
  }
}
