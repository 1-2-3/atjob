package com.bzb.atjob.app.auth.feed.web;

import com.bzb.atjob.app.auth.core.application.RoleApplicationService;
import com.bzb.atjob.app.auth.core.entity.Role;
import com.bzb.atjob.common.basetypes.BaseController;
import com.bzb.atjob.common.vo.ApiResult;
import com.bzb.atjob.common.vo.PaggingResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping({"/api/v1/role"})
@Api(value = "/api/v1/role", tags = "角色")
public class RoleController extends BaseController {

  private final RoleApplicationService roleService;

  @RequestMapping(method = RequestMethod.GET, path = "testHelloworld")
  public ApiResult<Object> testHelloworld() {
    // 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
    var factory = new IniSecurityManagerFactory("classpath:shiro.ini");
    // 2、得到SecurityManager实例 并绑定给SecurityUtils
    org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
    SecurityUtils.setSecurityManager(securityManager);
    // 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

    try {
      // 4、登录，即身份验证
      subject.login(token);
    } catch (AuthenticationException e) {
      // 5、身份验证失败
    }

    System.out.println("=== login ===");
    System.out.println("subject.isAuthenticated(): " + subject.isAuthenticated()); // 断言用户已经登录

    // 6、退出
    subject.logout();

    return ApiResult.success();
  }

  /**
   * 获取角色列表.
   *
   * @param pageNum 页号
   * @param pageSize 每页条数
   * @param sort 排序
   * @param query 模糊查询
   * @return
   */
  @ApiOperation(value = "获取角色列表", notes = "")
  @RequestMapping(method = RequestMethod.GET, path = "getRoleList")
  public ApiResult<List<Role>> getRoleList(
      @ApiParam(value = "页号", required = false) @RequestParam(required = false) Long pageNum,
      @ApiParam(value = "每页条数", required = false) @RequestParam(required = false) Long pageSize,
      @ApiParam(value = "排序", required = false) @RequestParam(required = false) String sort,
      @ApiParam(value = "模糊查询", required = false) @RequestParam(required = false) String query) {

    PaggingResult<Role> result = roleService.getRoleList(pageNum, pageSize, sort, query);
    return ApiResult.successDataTotal(result.getData(), result.getTotal());
  }

  /**
   * 获取匹配主键的角色.
   *
   * @param roleId 主键
   * @return
   */
  @ApiOperation(value = "获取匹配主键的角色")
  @RequestMapping(method = RequestMethod.GET, path = "getRoleById")
  public ApiResult<Role> getRoleById(@RequestParam(required = true) String roleId) {
    return ApiResult.successData(roleService.getRoleById(roleId));
  }

  /**
   * 保存或更新角色.
   *
   * @param entity 角色实体
   * @return
   */
  @ApiOperation(value = "保存或更新角色")
  @RequestMapping(method = RequestMethod.POST, path = "saveRole")
  public ApiResult<Object> savePage(@RequestBody Role entity) {
    roleService.saveRole(entity);
    return ApiResult.success();
  }

  /**
   * 删除角色.
   *
   * @param roleId 主键
   * @return
   */
  @ApiOperation(value = "删除角色")
  @RequestMapping(method = RequestMethod.POST, path = "deleteRole")
  public ApiResult<Object> deleteRole(
      @ApiParam(value = "主键", required = true) @RequestParam(required = true) String roleId) {
    roleService.deleteRole(roleId);
    return ApiResult.success();
  }
}
