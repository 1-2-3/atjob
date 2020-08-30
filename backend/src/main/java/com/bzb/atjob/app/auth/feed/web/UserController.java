package com.bzb.atjob.app.auth.feed.web;

import com.bzb.atjob.app.auth.core.application.UserApplicationService;
import com.bzb.atjob.app.auth.core.entity.User;
import com.bzb.atjob.common.basetypes.BaseController;
import com.bzb.atjob.common.vo.ApiResult;
import com.bzb.atjob.common.vo.PaggingResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping({"/api/v1/user"})
@Api(value = "/api/v1/user", tags = "用户")
public class UserController extends BaseController {

  private final UserApplicationService userService;

  /**
   * 获取用户列表.
   *
   * @param pageNum 页号
   * @param pageSize 每页条数
   * @param sort 排序
   * @param query 模糊查询
   * @return
   */
  @ApiOperation(value = "获取用户列表", notes = "")
  @RequestMapping(method = RequestMethod.GET, path = "getUserList")
  public ApiResult<List<User>> getUserList(
      @ApiParam(value = "页号", required = false) @RequestParam(required = false) Long pageNum,
      @ApiParam(value = "每页条数", required = false) @RequestParam(required = false) Long pageSize,
      @ApiParam(value = "排序", required = false) @RequestParam(required = false) String sort,
      @ApiParam(value = "模糊查询", required = false) @RequestParam(required = false) String query) {

    PaggingResult<User> result = userService.getUserList(pageNum, pageSize, sort, query);
    return ApiResult.successDataTotal(result.getData(), result.getTotal());
  }

  /**
   * 获取匹配主键的用户.
   *
   * @param userId 主键
   * @return
   */
  @ApiOperation(value = "获取匹配主键的用户")
  @RequestMapping(method = RequestMethod.GET, path = "getUserById")
  public ApiResult<User> getUserById(@RequestParam(required = true) String userId) {
    return ApiResult.successData(userService.getUserById(userId));
  }

  /**
   * 保存或更新用户.
   *
   * @param entity 用户实体
   * @return
   */
  @ApiOperation(value = "保存或更新用户")
  @RequestMapping(method = RequestMethod.POST, path = "saveUser")
  public ApiResult<Object> savePage(@RequestBody User entity) {
    userService.saveUser(entity);
    return ApiResult.success();
  }

  /**
   * 删除用户.
   *
   * @param userId 主键
   * @return
   */
  @ApiOperation(value = "删除用户")
  @RequestMapping(method = RequestMethod.POST, path = "deleteUser")
  public ApiResult<Object> deleteUser(
      @ApiParam(value = "主键", required = true) @RequestParam(required = true) String userId) {
    userService.deleteUser(userId);
    return ApiResult.success();
  }

  /**
   * 重置为默认密码.
   *
   * @param userId 主键
   * @return
   */
  @ApiOperation(value = "重置为默认密码")
  @RequestMapping(method = RequestMethod.POST, path = "resetUserDefaultPwd")
  public ApiResult<Object> resetUserDefaultPwd(
      @ApiParam(value = "主键", required = true) @RequestParam(required = true) String userId) {
    userService.resetUserDefaultPwd(userId);
    return ApiResult.success();
  }
}
