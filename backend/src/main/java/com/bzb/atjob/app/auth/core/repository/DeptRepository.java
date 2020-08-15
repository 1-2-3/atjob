package com.bzb.atjob.app.auth.core.repository;

import javax.validation.ValidationException;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bzb.atjob.app.auth.core.entity.Dept;
import com.bzb.atjob.app.auth.core.mapper.DeptMapper;
import com.bzb.atjob.common.util.MybatisUtil;
import com.bzb.atjob.common.vo.PaggingResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeptRepository {

    @Autowired
    private DeptMapper deptMapper;

    public Dept byId(String id) {
        return deptMapper.selectById(id);
    }

    public void save(Dept entity) {
        // 验证编码不允许重复
        if (this.isDeptExists(entity.getDeptId(), entity.getCode())) {
            throw new ValidationException("编码不允许重复！");
        }

        if (StringUtils.isBlank(entity.getDeptId())) {
            deptMapper.insert(entity);
        } else {
            deptMapper.updateById(entity);
        }
    }

    public void delete(String id) {
        var hasChildDept = hasChildDept(id);
        if (hasChildDept) {
            throw new ValidationException("含有子部门，不允许删除！");
        }

        deptMapper.deleteById(id);
    }

    /**
     * 获取部门列表
     *
     * @param pageNum
     * @param pageSize
     * @param sort
     * @param query
     */
    public PaggingResult<Dept> getDeptList(Long pageNum, Long pageSize, String sort, String query) {
        Page<Dept> page = MybatisUtil.createPage(pageNum, pageSize);

        // 排序
        MybatisUtil.getOrderItemList(sort, Dept.class, true).stream().forEach(page::addOrder);

        // 筛选
        QueryWrapper<Dept> wrapper = new QueryWrapper<Dept>();
        wrapper.lambda()
                .nested(w -> w.like(Dept::getName, query).or().like(Dept::getCode, StringUtils.defaultString(query)));

        deptMapper.selectPage(page, wrapper);

        return new PaggingResult<Dept>(page.getRecords(), page.getSize());
    }

    /**
     * 判断部门信息是否已存在
     *
     * @param deptId
     * @return
     */
    private boolean isDeptExists(String deptId, String code) {
        if (StringUtils.isBlank(deptId)) {
            return deptMapper.selectCount(new QueryWrapper<Dept>().lambda().eq(Dept::getCode, code)) > 0;
        } else {
            return deptMapper.selectCount(
                    new QueryWrapper<Dept>().lambda().ne(Dept::getDeptId, deptId).eq(Dept::getCode, code)) > 0;
        }
    }

    /**
     * 判断是否有子部门
     *
     * @param deptId
     */
    private boolean hasChildDept(String deptId) {
        return deptMapper.selectCount(new QueryWrapper<Dept>().lambda().eq(Dept::getParent, deptId)) > 0;
    }
}