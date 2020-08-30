package com.bzb.atjob.app.auth.core.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bzb.atjob.app.auth.core.entity.Dept;
import com.bzb.atjob.app.auth.core.mapper.DeptMapper;
import com.bzb.atjob.common.util.MybatisUtil;
import com.bzb.atjob.common.vo.PaggingResult;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeptRepository {

  private final DeptMapper deptMapper;

  /**
   * 获取匹配主键的部门.
   *
   * @param id 主键
   * @return
   */
  public Dept byId(String id) {
    return deptMapper.selectById(id);
  }

  /**
   * 保存或更新部门.
   *
   * @param entity 部门实体
   */
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

  /**
   * 删除部门.
   *
   * @param id 部门Id
   */
  public void delete(String id) {
    var hasChildDept = hasChildDept(id);
    if (hasChildDept) {
      throw new ValidationException("含有子部门，不允许删除！");
    }

    deptMapper.deleteById(id);
  }

  /**
   * 获取部门列表.
   *
   * @param pageNum 页码
   * @param pageSize 每页条数
   * @param sort 排序
   * @param query 模糊查询字符串
   */
  public PaggingResult<Dept> getDeptList(Long pageNum, Long pageSize, String sort, String query) {
    Page<Dept> page = MybatisUtil.createPage(pageNum, pageSize);

    // 排序
    MybatisUtil.getOrderItemList(sort, Dept.class, true).stream().forEach(page::addOrder);

    // 筛选
    QueryWrapper<Dept> wrapper = new QueryWrapper<Dept>();
    wrapper
        .lambda()
        .nested(
            w ->
                w.like(Dept::getName, query)
                    .or()
                    .like(Dept::getCode, StringUtils.defaultString(query)));

    deptMapper.selectPage(page, wrapper);

    return new PaggingResult<Dept>(page.getRecords(), page.getSize());
  }

  /**
   * 判断部门信息是否已存在.
   *
   * @param deptId 部门Id
   * @return
   */
  private boolean isDeptExists(String deptId, String code) {
    if (StringUtils.isBlank(deptId)) {
      return deptMapper.selectCount(new QueryWrapper<Dept>().lambda().eq(Dept::getCode, code)) > 0;
    } else {
      return deptMapper.selectCount(
              new QueryWrapper<Dept>().lambda().ne(Dept::getDeptId, deptId).eq(Dept::getCode, code))
          > 0;
    }
  }

  /**
   * 判断是否有子部门.
   *
   * @param deptId 部门Id
   */
  private boolean hasChildDept(String deptId) {
    return deptMapper.selectCount(new QueryWrapper<Dept>().lambda().eq(Dept::getParent, deptId))
        > 0;
  }
}
