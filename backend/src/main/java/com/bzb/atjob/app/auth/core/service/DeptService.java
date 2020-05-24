package com.bzb.atjob.app.auth.core.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

import com.github.pagehelper.PageHelper;
import com.bzb.atjob.app.auth.core.entity.Dept;
import com.bzb.atjob.app.auth.core.repository.DeptMapper;
import com.bzb.atjob.app.auth.core.repository.DeptSpec;
import com.bzb.atjob.common.util.MybatisUtil;
import com.bzb.atjob.common.vo.PaggingResult;
import com.github.pagehelper.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class DeptService {
    @Autowired
    private DeptMapper deptMapper;
    // @Autowired
    // private UserMapper userMapper;

    public DeptService() {
    }

    /**
     * 查询所有部门集合
     *
     * @param pageNum
     * @param pageSize
     * @param sort
     * @param query
     * @return
     */
    public PaggingResult<Dept> getDeptList(Integer pageNum, Integer pageSize, String sort, String query) {
        DeptSpec spec = new DeptSpec();

        // 排序
        if (StringUtils.isNotBlank(sort)) {
            String orderByClause = MybatisUtil.getOrderByClause(sort, "deptId", spec::getFieldNameByPropName);
            spec.setOrderByClause(orderByClause);
        }

        DeptSpec.SpecCriteria criteria = spec.createCriteria();
        // 模糊查询
        if (query != null && query.length() > 0) {
            criteria.andQuery("%" + query + "%");
        }
        // 分页
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<Dept> list = deptMapper.selectByExample(spec);
        long total = 0;
        if (list instanceof Page) {
            total = ((Page<Dept>) list).getTotal();
        }
        return new PaggingResult<Dept>(list, total);
    }

    /**
     * 判断科室信息是否已存在
     *
     * @param deptId
     * @return
     */
    public boolean isDeptExists(String deptId, String code) {
        DeptSpec spec = new DeptSpec();
        DeptSpec.Criteria criteria = spec.createCriteria();
        criteria.andDeptIdNotEqualTo(deptId);
        criteria.andCodeEqualTo(code);
        return deptMapper.countByExample(spec) > 0;
    }

    /**
     * 按照id查询科室
     *
     * @param deptId
     * @return
     */
    public Dept getDeptById(String deptId) {
        DeptSpec spec = new DeptSpec();
        DeptSpec.Criteria criteria = spec.createCriteria();
        criteria.andDeptIdEqualTo(deptId);
        List<Dept> result = deptMapper.selectByExample(spec);

        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    /**
     * 新增科室节点
     *
     * @param dept
     */
    public void saveDept(Dept dept) {
        if (StringUtils.isBlank(dept.getDeptId())) {
            dept.setDeptId(UUID.randomUUID().toString());
        }

        // 验证编码不允许重复
        if (this.isDeptExists(dept.getDeptId(), dept.getCode())) {
            throw new ValidationException("编码不允许重复！");
        }

        deptMapper.insertSelective(dept);
    }

    /**
     * 修改科室节点
     *
     * @param dept
     */
    public void updateDept(Dept dept) {
        // 验证编码不允许重复
        if (this.isDeptExists(dept.getDeptId(), dept.getCode())) {
            throw new ValidationException("编码不允许重复！");
        }

        deptMapper.updateByPrimaryKeySelective(dept);
    }

    /**
     * 删除科室节点
     *
     * @param deptId
     */
    public void deleteDept(String deptId) {
        var hasChildDept = this.hasChildDept(deptId);
        if (hasChildDept) {
            throw new ValidationException("含有子部门，不允许删除！");
        }

        deptMapper.deleteByPrimaryKey(deptId);
    }

    /**
     * 判断是否有子部门
     * 
     * @param deptId
     * @return
     */
    private boolean hasChildDept(String deptId) {
        var spec = new DeptSpec();
        var criteria = spec.createCriteria();
        criteria.andParentEqualTo(deptId);
        var count = deptMapper.countByExample(spec);
        return count > 0;
    }
}