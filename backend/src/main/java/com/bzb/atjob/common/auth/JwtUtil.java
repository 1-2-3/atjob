package com.bzb.atjob.common.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/** Jwt 相关工具方法. */
public class JwtUtil {

  private static final long EXPIRE_TIME = 24 * 60 * 1000 * 1000;

  /**
   * 默认密码.
   *
   * <p>更安全的做法是使用每个用户的密码生成和验证token，代价是每次请求验证token时都必须访问数据库获取用户的密码。 如果将用户名和密码放入 redis
   * 中虽然可以提升性能，但增加了系统复杂性。
   */
  private static final String DEFAULT_SECRET = "TR2Uhe2zlUX6JlWwnzn0Ve9MhEE6r";

  /**
   * 校验 token是否正确.
   *
   * @param token 密钥
   * @param secret 用户的密码
   * @return 是否正确
   */
  public static boolean verify(String token, String username, String secret) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
      verifier.verify(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 校验 token是否正确.
   *
   * @param token 密钥
   * @return 是否正确
   */
  public static boolean verify(String token, String username) {
    return verify(token, username, DEFAULT_SECRET);
  }

  /**
   * 从 token中获取用户名.
   *
   * @return token中包含的用户名
   */
  public static String getUsername(String token) {
    try {
      DecodedJWT jwt = JWT.decode(token);
      return jwt.getClaim("username").asString();
    } catch (JWTDecodeException e) {
      return null;
    }
  }

  /**
   * 从 token中获取用户Id.
   *
   * @return token中包含的用户Id
   */
  public static String getUserid(String token) {
    try {
      DecodedJWT jwt = JWT.decode(token);
      return jwt.getClaim("userid").asString();
    } catch (JWTDecodeException e) {
      return null;
    }
  }

  /**
   * 生成 token.
   *
   * @param username 用户名
   * @param secret 用户的密码
   * @return token
   */
  public static String sign(String username, String userId, String secret) {
    try {
      username = StringUtils.lowerCase(username);
      Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
          .withClaim("username", username)
          .withClaim("userid", userId)
          .withExpiresAt(date)
          .sign(algorithm);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 生成 token.
   *
   * @param username 用户名
   * @return token
   */
  public static String sign(String username, String userId) {
    return sign(username, userId, DEFAULT_SECRET);
  }
}
