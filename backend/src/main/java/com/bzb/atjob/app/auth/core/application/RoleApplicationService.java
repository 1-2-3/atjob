package com.bzb.atjob.app.auth.core.application;

import com.bzb.atjob.app.auth.core.entity.Role;
import com.bzb.atjob.app.auth.core.repository.RoleRepository;
import com.bzb.atjob.common.vo.PaggingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 角色管理应用服务. */
@Service
@Transactional()
@RequiredArgsConstructor
public class RoleApplicationService {

  private final RoleRepository roleRepository;

  /**
   * 获取角色列表.
   *
   * @param pageNum 页码
   * @param pageSize 每页条数
   * @param sort 排序
   * @param query 模糊查询字符串
   * @return
   */
  public PaggingResult<Role> getRoleList(Long pageNum, Long pageSize, String sort, String query) {
    return roleRepository.getRoleList(pageNum, pageSize, sort, query);
  }

  /**
   * 获取匹配主键的角色.
   *
   * @param pageId 角色Id
   * @return
   */
  public Role getRoleById(String pageId) {
    return roleRepository.byId(pageId);
  }

  /**
   * 保存或更新角色.
   *
   * @param role 角色实体
   */
  public void saveRole(Role role) {
    roleRepository.save(role);
  }

  /** 删除角色. */
  public void deleteRole(String roleId) {
    roleRepository.delete(roleId);
  }
}
