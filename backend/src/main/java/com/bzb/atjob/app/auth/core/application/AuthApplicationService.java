package com.bzb.atjob.app.auth.core.application;

import com.bzb.atjob.app.auth.core.entity.User;
import com.bzb.atjob.app.auth.core.repository.UserRepository;
import com.bzb.atjob.common.auth.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 权限认证应用服务. */
@Service
@Transactional
@RequiredArgsConstructor
public class AuthApplicationService {
  private final UserRepository userRepository;

  /**
   * 用户登录.
   *
   * @param loginName 登录名
   * @param plainPwd 原始密码
   * @return token
   */
  public String login(String loginName, String plainPwd) {
    User user = userRepository.getByLoginName(loginName);

    boolean suc = user != null && !user.getIsStop() && user.verifyPwd(plainPwd);

    if (suc) {
      return JwtUtil.sign(loginName);
    } else {
      return null;
    }
  }
}
