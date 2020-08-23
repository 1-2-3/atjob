package com.bzb.atjob.app.auth.core.application;

import com.bzb.atjob.app.auth.core.entity.User;
import com.bzb.atjob.app.auth.core.repository.UserRepository;
import com.bzb.atjob.common.vo.PaggingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 用户应用服务. */
@Service
@Transactional
public class UserApplicationService {
  @Autowired UserRepository userRepository;

  /**
   * 获取用户列表.
   *
   * @param pageNum 页码
   * @param pageSize 每页条数
   * @param sort 排序
   * @param query 模糊查询字符串
   * @return
   */
  public PaggingResult<User> getUserList(Long pageNum, Long pageSize, String sort, String query) {
    return userRepository.getUserList(pageNum, pageSize, sort, query);
  }

  /**
   * 获取匹配主键的用户.
   *
   * @param userId 用户Id
   * @return
   */
  public User getUserById(String userId) {
    return userRepository.byId(userId);
  }

  /**
   * 保存或更新用户.
   *
   * @param user 用户实体
   */
  public void saveUser(User user) {
    userRepository.save(user);
  }

  /** 删除用户. */
  public void deleteUser(String userId) {
    userRepository.delete(userId);
  }

  /** 将指定用户密码重置为默认密码. */
  public void resetUserDefaultPwd(String userId) {
    User user = userRepository.byId(userId);
    user.resetToDefaultPwd();
    userRepository.save(user);
  }
}
