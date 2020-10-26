package com.bzb.atjob.app.auth.feed.web;

import com.bzb.atjob.app.auth.core.user.application.AuthApplicationService;
import com.bzb.atjob.common.basetypes.BaseController;
import com.bzb.atjob.common.vo.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping({"/api/v1/auth"})
@Api(value = "/api/v1/auth", tags = "权限认证")
public class AuthController extends BaseController {

  private final AuthApplicationService authService;

  /**
   * 用户登录.
   *
   * @param loginName 登录名
   * @param pwd 密码
   * @return 成功返回 token，失败返回 null
   */
  @ApiOperation(value = "用户登录")
  @RequestMapping(method = RequestMethod.POST, path = "login")
  public ApiResult<String> login(
      @ApiParam(value = "登录名", required = true) @RequestParam(required = true) String loginName,
      @ApiParam(value = "密码", required = true) @RequestParam(required = true) String pwd) {
    String token = authService.login(loginName, pwd);
    return ApiResult.successData(token);
  }
}
