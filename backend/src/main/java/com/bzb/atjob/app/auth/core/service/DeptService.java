package com.bzb.atjob.app.auth.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

import com.github.pagehelper.PageHelper;
import com.bzb.atjob.app.auth.core.entity.Dept;
import com.bzb.atjob.app.auth.core.repository.DeptMapper;
import com.bzb.atjob.app.auth.core.repository.DeptSpec;
import com.bzb.atjob.common.vo.PaggingResult;
import com.github.pagehelper.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
     * @param orderByName
     * @param orderByCreateTime
     * @param query
     * @return
     */
    public PaggingResult<Dept> getDeptList(Integer pageNum, Integer pageSize, String orderByName, String orderByCode,
            String orderByCreateTime, String query) {
        DeptSpec spec = new DeptSpec();
        // 排序
        String orderByClause = String.format("%s desc, %s", spec.getFieldNameByPropName("indexField"),
                spec.getFieldNameByPropName("deptId"));
        if (orderByName != null && orderByName.length() > 0) {
            orderByClause = String.format("%s %s, %s", spec.getFieldNameByPropName("name"), orderByName,
                    spec.getFieldNameByPropName("deptId"));
        }
        if (orderByCode != null && orderByCode.length() > 0) {
            orderByClause = String.format("%s %s, %s", spec.getFieldNameByPropName("orderByCode"), orderByCode,
                    spec.getFieldNameByPropName("deptId"));
        }
        if (orderByCreateTime != null && orderByCreateTime.length() > 0) {
            orderByClause = String.format("%s %s, %s", spec.getFieldNameByPropName("orderByCreateTime"),
                    orderByCreateTime, spec.getFieldNameByPropName("deptId"));
        }
        spec.setOrderByClause(orderByClause);
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
        if (deptId != null && deptId.length() > 0) {
            criteria.andDeptIdNotEqualTo(deptId);
        }
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
        // 必须判断 deptId 不能为空！否则传递为空id会出现系统bug add by congjiajia at 2019-1-17
        if (deptId == null || deptId.trim().length() == 0)
            throw new ValidationException("通过部门id 查询部门，却id为空！");
        Dept dept = new Dept();
        DeptSpec spec = new DeptSpec();
        DeptSpec.Criteria criteria = spec.createCriteria();
        if (deptId != null || deptId.length() > 0) {
            criteria.andDeptIdEqualTo(deptId);
        }
        List<Dept> result = deptMapper.selectByExample(spec);
        if (result.size() > 0) {
            dept = result.get(0);
        }
        return dept;
    }

    /**
     * 新增科室节点
     *
     * @param dept
     */
    public void saveDept(Dept dept) {
        // 判断用于新增医院时自动创建科室，创建医院时自动生成科室Id，医院编码，创建时间不需要手动生成。
        if (dept.getDeptId() == null || dept.getDeptId().trim().length() == 0) {
            dept.setDeptId(UUID.randomUUID().toString());
        }

        // 重新构建树结构
        // 创建本节点及其子节点集合
        // List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        // treeNodes.add(dept);
        // 分情况调用构建树结构的工具类
        Dept parent = null;
        if (!(dept.getParent() == null || dept.getParent().trim().length() == 0)) {
            parent = deptMapper.selectByPrimaryKey(dept.getParent());
        }
        // TreeModelTools.rebuildTree(parent, treeNodes);
        deptMapper.insertSelective(dept);
    }

    /**
     * 修改科室节点
     *
     * @param dept
     */
    public void updateDept(Dept dept) {
        // 调用TreeModelTools工具类处理数据
        // 获取本身以及子节点
        List<Dept> childDepts = this.getChildDeptList(dept.getTreeIds());
        // 将数据库里原始节点替换为要修改的节点
        childDepts.removeIf(p -> p.getDeptId().equals(dept.getDeptId()));
        childDepts.add(dept);
        // 循环创建TreeNode集合
        // List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        // treeNodes.addAll(childDepts);
        // 获取父节点
        Dept parent = null;
        if (!(dept.getParent() == null || dept.getParent().trim().length() == 0)) {
            parent = deptMapper.selectByPrimaryKey(dept.getParent());
        }
        // TreeModelTools.rebuildTree(parent, treeNodes);

        for (Dept depteTemp : childDepts) {
            deptMapper.updateByPrimaryKeySelective(depteTemp);
        }
    }

    /**
     * 删除科室节点
     *
     * @param deptId
     */
    public void deleteDept(String deptId) {
        Dept dept = deptMapper.selectByPrimaryKey(deptId);
        // 获取IDS相同的节点判断是否为父节点
        List<Dept> depts = this.getChildDeptList(dept.getTreeIds());
        // 判断是否是父节点
        boolean isParent = false;
        for (Dept deptTemp : depts) {
            if (deptTemp.getParent() != null && deptTemp.getParent().equals(deptId)) {
                isParent = true;
            }
        }
        if (isParent) {
            // 如果是父节点不可删除
            throw new ValidationException("科室层级不允许修改");
        } else {
            // UserSpec example = new UserSpec();
            // UserSpec.SpecCriteria criteria = example.createCriteria();
            // criteria.andDeptIdEqualTo(deptId);
            // Long count = userMapper.countByExample(example);
            // if (count > 0) {
            // // 存在其他关联字典数据不可删除
            // throw new ValidationException(String.format("【%s】下有：%s 条关联数据，不允许删除！",
            // dept.getName(), count));
            // } else {
            // // 不是父节点可以删除
            // deptMapper.deleteByPrimaryKey(deptId);
            // }
        }
    }

    /**
     * 获取包括自身的全部子节点
     *
     * @param ids
     * @return
     */
    public List<Dept> getChildDeptList(String ids) {
        DeptSpec spec = new DeptSpec();
        DeptSpec.Criteria criteria = spec.createCriteria();
        criteria.andTreeIdsLike(ids + "%");
        List<Dept> childDepts = deptMapper.selectByExample(spec);
        return childDepts;
    }
}