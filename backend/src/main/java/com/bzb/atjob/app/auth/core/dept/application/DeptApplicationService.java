package com.bzb.atjob.app.auth.core.dept.application;

import com.bzb.atjob.app.auth.core.dept.model.Dept;
import com.bzb.atjob.app.auth.core.dept.repository.DeptRepository;
import com.bzb.atjob.common.vo.PaggingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 部门管理应用服务. */
@Service
@Transactional
@RequiredArgsConstructor
public class DeptApplicationService {
  private final DeptRepository deptRepository;

  /**
   * 获取部门列表.
   *
   * @param pageNum 页码
   * @param pageSize 每页条数
   * @param sort 排序
   * @param query 模糊查询字符串
   * @return
   */
  public PaggingResult<Dept> getDeptList(Long pageNum, Long pageSize, String sort, String query) {
    return deptRepository.getDeptList(pageNum, pageSize, sort, query);
  }

  /**
   * 获取匹配主键的部门.
   *
   * @param deptId 部门Id
   * @return
   */
  public Dept getDeptById(String deptId) {
    return deptRepository.byId(deptId);
  }

  /**
   * 保存或更新部门.
   *
   * @param dept 部门实体
   */
  public void saveDept(Dept dept) {
    deptRepository.save(dept);
  }

  /**
   * 删除部门.
   *
   * @param deptId 部门主键
   */
  public void deleteDept(String deptId) {
    deptRepository.delete(deptId);
  }
}
