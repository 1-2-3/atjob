package com.bzb.atjob.app.auth.core.application;

import com.bzb.atjob.app.auth.core.entity.Dept;
import com.bzb.atjob.app.auth.core.repository.DeptRepository;
import com.bzb.atjob.common.vo.PaggingResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 部门管理应用服务
 */
@Service
@Transactional
public class DeptApplicationService {
    @Autowired
    DeptRepository deptRepository;

    public DeptApplicationService() {
    }

    /**
     * 获取部门列表
     *
     * @param pageNum
     * @param pageSize
     * @param sort
     * @param query
     * @return
     */
    public PaggingResult<Dept> getDeptList(Long pageNum, Long pageSize, String sort, String query) {
        return deptRepository.getDeptList(pageNum, pageSize, sort, query);
    }

    /**
     * 获取匹配主键的部门
     *
     * @param deptId
     * @return
     */
    public Dept getDeptById(String deptId) {
        return deptRepository.byId(deptId);
    }

    /**
     * 保存或更新部门
     *
     * @param dept
     */
    public void saveDept(Dept dept) {
        deptRepository.save(dept);
    }

    /**
     * 删除部门
     *
     * @param deptId
     */
    public void deleteDept(String deptId) {
        deptRepository.delete(deptId);
    }
}