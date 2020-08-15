package com.bzb.atjob.app.auth.core.application;

import com.bzb.atjob.app.auth.core.entity.Role;
import com.bzb.atjob.app.auth.core.repository.RoleRepository;
import com.bzb.atjob.common.vo.PaggingResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色管理应用服务
 */
@Service
@Transactional
public class RoleApplicationService {

    @Autowired
    RoleRepository roleRepository;

    /**
     * 获取角色列表
     *
     * @param pageNum
     * @param pageSize
     * @param sort
     * @param query
     * @return
     */
    public PaggingResult<Role> getRoleList(Long pageNum, Long pageSize, String sort, String query) {
        return roleRepository.getRoleList(pageNum, pageSize, sort, query);
    }

    /**
     * 获取匹配主键的角色
     *
     * @param pageId
     * @return
     */
    public Role getRoleById(String pageId) {
        return roleRepository.byId(pageId);
    }

    /**
     * 保存或更新角色
     *
     * @param page
     */
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    /**
     * 删除角色
     *
     */
    public void deleteRole(String roleId) {
        roleRepository.delete(roleId);
    }
}