package com.bzb.atjob.app.auth.core.application;

import com.bzb.atjob.app.auth.core.entity.Page;
import com.bzb.atjob.app.auth.core.repository.PageRepository;
import com.bzb.atjob.common.vo.PaggingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 页面管理应用服务. */
@Service
@Transactional
public class PageApplicationService {
  @Autowired PageRepository pageRepository;

  public PageApplicationService() {}

  /**
   * 获取页面列表.
   *
   * @param pageNum 页码
   * @param pageSize 每页条数
   * @param sort 排序
   * @param query 模糊查询字符串
   * @return
   */
  public PaggingResult<Page> getPageList(Long pageNum, Long pageSize, String sort, String query) {
    return pageRepository.getPageList(pageNum, pageSize, sort, query);
  }

  /**
   * 获取匹配主键的页面.
   *
   * @param pageId 页面Id
   * @return
   */
  public Page getPageById(String pageId) {
    return pageRepository.byId(pageId);
  }

  /**
   * 保存或更新页面.
   *
   * @param page 页面实体
   */
  public void savePage(Page page) {
    pageRepository.save(page);
  }

  /**
   * 删除页面.
   *
   * @param pageId 页面Id
   */
  public void deletePage(String pageId) {
    pageRepository.delete(pageId);
  }
}
