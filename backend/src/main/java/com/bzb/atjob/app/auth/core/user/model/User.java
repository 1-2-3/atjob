package com.bzb.atjob.app.auth.core.user.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@ApiModel(description = "系统用户")
@Data
@TableName(value = "AUTH_USER")
public class User {
  @JsonIgnore
  @TableField(exist = false)
  private PasswordEncoder passwordEncoder =
      PasswordEncoderFactories.createDelegatingPasswordEncoder();

  @TableId(type = IdType.ASSIGN_ID)
  @ApiModelProperty(value = "USER_ID", required = false)
  private String userId;

  @ApiModelProperty(value = "DEPT_ID", required = false)
  private String deptId;

  @ApiModelProperty(value = "部门名称", required = false)
  private String deptName;

  @ApiModelProperty(value = "用户显示名", required = false)
  private String name;

  @ApiModelProperty(value = "用户编码", required = false)
  private String code;

  @ApiModelProperty(value = "登录名", required = false)
  private String loginName;

  @ApiModelProperty(value = "密码", required = false)
  @Setter(AccessLevel.PRIVATE)
  private String pwd;

  @ApiModelProperty(value = "录入码", required = false)
  private String inputCode;

  @ApiModelProperty(value = "排序", required = false)
  private Integer indexField;

  @ApiModelProperty(value = "描述", required = false)
  private String description;

  @ApiModelProperty(value = "电话", required = false)
  private String phone;

  @ApiModelProperty(value = "是否停用", required = false)
  private Boolean isStop;

  @TableField(fill = FieldFill.INSERT)
  @ApiModelProperty(value = "创建时间", required = false)
  private Date createTime;

  @TableField(fill = FieldFill.INSERT)
  @ApiModelProperty(value = "创建人Id@AUTH_USER", required = false)
  private String createUserId;

  @TableField(fill = FieldFill.UPDATE)
  @ApiModelProperty(value = "修改人id@AUTH_USER", required = false)
  private String modifyUserId;

  @TableField(fill = FieldFill.UPDATE)
  @ApiModelProperty(value = "修改时间", required = false)
  private Date modifyTime;

  @JsonIgnore
  @TableField(exist = false)
  @ApiModelProperty(value = "拥有的角色", required = false)
  List<RoleOwnedByUser> rolesOwned;

  /**
   * 拥有的角色Id列表.
   *
   * @return
   */
  @ApiModelProperty(value = "拥有的角色Id列表", required = false)
  public List<String> getRoleIdListOwned() {
    return this.rolesOwned == null
        ? null
        : this.rolesOwned.stream().map(t -> t.getRoleId()).collect(Collectors.toList());
  }

  /**
   * 设置拥有的角色Id列表.
   *
   * @param roleIdList 拥有的角色Id列表
   */
  @ApiModelProperty(value = "拥有的角色Id列表")
  public void setRoleIdListOwned(List<String> roleIdList) {
    if (roleIdList == null) {
      return;
    }

    var newRolesOwned =
        roleIdList.stream()
            .map(
                roleId -> RoleOwnedByUser.builder().userId(this.getUserId()).roleId(roleId).build())
            .collect(Collectors.toList());

    this.setRolesOwned(newRolesOwned);
  }

  /**
   * 设置主键.
   *
   * @param userId 主键
   */
  public void setUserId(String userId) {
    this.userId = userId;

    if (this.rolesOwned != null) {
      var newRolesOwned =
          this.rolesOwned.stream()
              .map(
                  t ->
                      RoleOwnedByUser.builder()
                          .userRoleId(t.getUserRoleId())
                          .userId(userId)
                          .roleId(t.getRoleId())
                          .build())
              .collect(Collectors.toList());

      this.setRolesOwned(newRolesOwned);
    }
  }

  /**
   * 重设密码。将 newPwd 转换为 Hash 值存入 pwd 中.
   *
   * @param newPwd 新密码明文
   */
  public void resetPwd(String newPwd) {
    String hashPwd = passwordEncoder.encode(newPwd);
    setPwd(hashPwd);
  }

  /** 重置密码. */
  public void resetToDefaultPwd() {
    this.resetPwd("0");
  }

  /**
   * 验证密码.
   *
   * @param plainPwd 密码原文
   * @return
   */
  public boolean verifyPwd(String plainPwd) {
    if (StringUtils.isBlank(plainPwd)) {
      return false;
    }

    return passwordEncoder.matches(plainPwd, this.getPwd());
  }
}
