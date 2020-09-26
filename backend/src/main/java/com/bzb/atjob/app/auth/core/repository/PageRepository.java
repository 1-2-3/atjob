package com.bzb.atjob.app.auth.core.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bzb.atjob.app.auth.core.entity.Page;
import com.bzb.atjob.app.auth.core.mapper.PageMapper;
import com.bzb.atjob.common.util.MybatisUtil;
import com.bzb.atjob.common.vo.PaggingResult;
import java.util.List;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageRepository {

  private final PageMapper pageMapper;

  /**
   * 获取匹配主键的页面.
   *
   * @param id 主键
   * @return
   */
  public Page byId(String id) {
    return pageMapper.selectById(id);
  }

  /**
   * 保存或更新页面.
   *
   * @param entity 页面实体
   */
  public void save(Page entity) {
    // 验证编码不允许重复
    if (this.isPageExists(entity.getPageId(), entity.getCode())) {
      throw new ValidationException("编码不允许重复！");
    }

    if (StringUtils.isBlank(entity.getPageId())) {
      pageMapper.insert(entity);
    } else {
      pageMapper.updateById(entity);
    }
  }

  /**
   * 删除页面.
   *
   * @param id 页面Id
   */
  public void delete(String id) {
    var hasChildPage = hasChildPage(id);
    if (hasChildPage) {
      throw new ValidationException("含有子页面，不允许删除！");
    }

    pageMapper.deleteById(id);
  }

  /**
   * 获取页面列表.
   *
   * @param pageNum 页码
   * @param pageSize 每页条数
   * @param sort 排序
   * @param query 模糊查询字符串
   */
  public PaggingResult<Page> getPageList(Long pageNum, Long pageSize, String sort, String query) {
    com.baomidou.mybatisplus.extension.plugins.pagination.Page<Page> page =
        MybatisUtil.createPage(pageNum, pageSize);

    // 排序
    MybatisUtil.getOrderItemList(sort, Page.class, true).stream().forEach(page::addOrder);

    // 筛选
    QueryWrapper<Page> wrapper = new QueryWrapper<Page>();
    wrapper
        .lambda()
        .nested(
            w ->
                w.like(Page::getName, query)
                    .or()
                    .like(Page::getCode, StringUtils.defaultString(query)));

    pageMapper.selectPage(page, wrapper);

    return new PaggingResult<Page>(page.getRecords(), page.getSize());
  }

  /** 获取全部可见的页面权限列表. */
  public List<Page> getAvailableAll() {
    return pageMapper.selectList(
        new QueryWrapper<Page>()
            .lambda()
            .eq(Page::getIsStop, false)
            .eq(Page::getIsHide, false)
            .orderByAsc(Page::getIndexField, Page::getName));
  }

  /**
   * 判断页面信息是否已存在.
   *
   * @param pageId 页面Id
   * @return
   */
  private boolean isPageExists(String pageId, String code) {
    if (StringUtils.isBlank(pageId)) {
      return pageMapper.selectCount(new QueryWrapper<Page>().lambda().eq(Page::getCode, code)) > 0;
    } else {
      return pageMapper.selectCount(
              new QueryWrapper<Page>().lambda().ne(Page::getPageId, pageId).eq(Page::getCode, code))
          > 0;
    }
  }

  /**
   * 判断是否有子页面.
   *
   * @param pageId 页面Id
   */
  private boolean hasChildPage(String pageId) {
    return pageMapper.selectCount(new QueryWrapper<Page>().lambda().eq(Page::getParent, pageId))
        > 0;
  }
}
