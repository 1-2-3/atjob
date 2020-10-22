package com.bzb.atjob.app.auth.core.application;

import com.bzb.atjob.app.auth.core.model.Role;
import com.bzb.atjob.app.auth.core.model.User;
import com.bzb.atjob.app.auth.core.repository.PageRepository;
import com.bzb.atjob.app.auth.core.repository.RoleRepository;
import com.bzb.atjob.app.auth.core.repository.UserRepository;
import com.bzb.atjob.common.vo.PaggingResult;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 用户应用服务. */
@Service
@Transactional
@RequiredArgsConstructor
public class UserApplicationService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PageRepository pageRepository;

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
   * 获取匹配登录名的用户.
   *
   * @param loginName 用户登录名
   * @return
   */
  public User getUserByLoginName(String loginName) {
    return userRepository.getByLoginName(loginName);
  }

  /** 获取用户拥有的页面权限列表. */
  public List<com.bzb.atjob.app.auth.core.model.Page> getPagesOwnedByUser(String loginName) {
    User user = userRepository.getByLoginName(loginName);

    if (user == null) {
      throw new ValidationException("用户不存在！");
    }

    List<String> roleIdList = user.getRoleIdListOwned();
    Set<String> pageIdsOwned = new HashSet<String>();

    for (String roleId : roleIdList) {
      Role role = roleRepository.byId(roleId);
      pageIdsOwned.addAll(role.getPageIdListOwned());
    }

    List<com.bzb.atjob.app.auth.core.model.Page> allPages = pageRepository.getAvailableAll();

    // 由于 pageIdsOwned 只包含叶子节点，还要附加上父节点
    Set<String> ownedNodePageIds =
        allPages.stream()
            .filter(t -> pageIdsOwned.contains(t.getPageId()))
            .map(t -> t.getParent())
            .collect(Collectors.toSet());

    return allPages.stream()
        .filter(
            t -> ownedNodePageIds.contains(t.getPageId()) || pageIdsOwned.contains(t.getPageId()))
        .collect(Collectors.toList());
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
