package com.bzb.atjob.app.auth.feed.web;

import com.bzb.atjob.app.auth.core.application.PageApplicationService;
import com.bzb.atjob.app.auth.core.entity.Page;
import com.bzb.atjob.common.basetypes.BaseController;
import com.bzb.atjob.common.vo.ApiResult;
import com.bzb.atjob.common.vo.PaggingResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/v1/page"})
@Api(value = "/api/v1/page", tags = "页面")
public class PageController extends BaseController {

  @Autowired PageApplicationService pageService;

  /**
   * 获取页面列表.
   *
   * @param pageNum 页号
   * @param pageSize 每页条数
   * @param sort 排序
   * @param query 模糊查询
   * @return
   */
  @ApiOperation(value = "获取页面列表", notes = "")
  @RequestMapping(method = RequestMethod.GET, path = "getPageList")
  public ApiResult<List<Page>> getPageList(
      @ApiParam(value = "页号", required = false) @RequestParam(required = false) Long pageNum,
      @ApiParam(value = "每页条数", required = false) @RequestParam(required = false) Long pageSize,
      @ApiParam(value = "排序", required = false) @RequestParam(required = false) String sort,
      @ApiParam(value = "模糊查询", required = false) @RequestParam(required = false) String query) {

    PaggingResult<Page> result = pageService.getPageList(pageNum, pageSize, sort, query);
    return ApiResult.successDataTotal(result.getData(), result.getTotal());
  }

  /**
   * 获取匹配主键的页面.
   *
   * @param pageId 主键
   * @return
   */
  @ApiOperation(value = "获取匹配主键的页面")
  @RequestMapping(method = RequestMethod.GET, path = "getPageById")
  public ApiResult<Page> getPageById(@RequestParam(required = true) String pageId) {
    return ApiResult.successData(pageService.getPageById(pageId));
  }

  /**
   * 保存或更新页面.
   *
   * @param entity 页面实体
   * @return
   */
  @ApiOperation(value = "保存或更新页面")
  @RequestMapping(method = RequestMethod.POST, path = "savePage")
  public ApiResult<Object> savePage(@RequestBody Page entity) {
    pageService.savePage(entity);
    return ApiResult.success();
  }

  /** 删除页面. */
  @ApiOperation(value = "删除页面")
  @RequestMapping(method = RequestMethod.POST, path = "deletePage")
  public ApiResult<Object> deletePage(
      @ApiParam(value = "主键", required = true) @RequestParam(required = true) String pageId) {
    pageService.deletePage(pageId);
    return ApiResult.success();
  }
}
