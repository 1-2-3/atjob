package com.bzb.atjob.app.auth.core.repository;

import java.util.List;

import javax.validation.ValidationException;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bzb.atjob.app.auth.core.entity.PageOwnedByRole;
import com.bzb.atjob.app.auth.core.entity.Role;
import com.bzb.atjob.app.auth.core.mapper.PageOwnedByRoleMapper;
import com.bzb.atjob.app.auth.core.mapper.RoleMapper;
import com.bzb.atjob.common.util.MybatisUtil;
import com.bzb.atjob.common.vo.PaggingResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleRepository {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PageOwnedByRoleMapper pageOwnedByRoleMapper;

    public Role byId(String id) {
        Role entity = roleMapper.selectById(id);

        var pagesOwend = pageOwnedByRoleMapper
                .selectList(new LambdaQueryWrapper<PageOwnedByRole>().eq(PageOwnedByRole::getRoleId, id));
        entity.setPagesOwned(pagesOwend);

        return entity;
    }

    public void save(Role entity) {
        // 验证编码不允许重复
        if (this.isRoleExists(entity.getRoleId(), entity.getCode())) {
            throw new ValidationException("编码不允许重复！");
        }

        if (StringUtils.isBlank(entity.getRoleId())) {
            roleMapper.insert(entity);
            this.savePagesOwned(entity.getRoleId(), entity.getPagesOwned());
        } else {
            roleMapper.updateById(entity);
            this.savePagesOwned(entity.getRoleId(), entity.getPagesOwned());
        }
    }

    private void savePagesOwned(String roleId, List<PageOwnedByRole> pagesOwned) {
        pageOwnedByRoleMapper.delete(new LambdaQueryWrapper<PageOwnedByRole>().eq(PageOwnedByRole::getRoleId, roleId));
        if (pagesOwned != null) {
            for (PageOwnedByRole pageOwnedByRole : pagesOwned) {
                pageOwnedByRoleMapper.insert(pageOwnedByRole);
            }
        }
    }

    public void delete(String id) {
        roleMapper.deleteById(id);
        pageOwnedByRoleMapper.delete(new LambdaQueryWrapper<PageOwnedByRole>().eq(PageOwnedByRole::getRoleId, id));
    }

    /**
     * 获取角色列表
     *
     * @param pageNum
     * @param pageSize
     * @param sort
     * @param query
     */
    public PaggingResult<Role> getRoleList(Long pageNum, Long pageSize, String sort, String query) {
        Page<Role> page = MybatisUtil.createPage(pageNum, pageSize);

        // 排序
        MybatisUtil.getOrderItemList(sort, Page.class, true).stream().forEach(page::addOrder);

        // 筛选
        QueryWrapper<Role> wrapper = new QueryWrapper<Role>();
        wrapper.lambda()
                .nested(w -> w.like(Role::getName, query).or().like(Role::getCode, StringUtils.defaultString(query)));

        roleMapper.selectPage(page, wrapper);

        return new PaggingResult<Role>(page.getRecords(), page.getSize());
    }

    /**
     * 判断角色是否已存在
     *
     */
    private boolean isRoleExists(String roldId, String code) {
        if (StringUtils.isBlank(roldId)) {
            return roleMapper.selectCount(new QueryWrapper<Role>().lambda().eq(Role::getCode, code)) > 0;
        } else {
            return roleMapper.selectCount(
                    new QueryWrapper<Role>().lambda().ne(Role::getRoleId, roldId).eq(Role::getCode, code)) > 0;
        }
    }
}