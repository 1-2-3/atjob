package com.bzb.atjob.app.auth.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

/** 被用户拥有的角色. */
@Value
@Builder
@TableName(value = "AUTH_USER_ROLE")
public class RoleOwnedByUser {
  @ApiModelProperty(value = "主键", required = false)
  @TableId(type = IdType.ASSIGN_ID)
  private String userRoleId;

  @ApiModelProperty(value = "用户Id", required = false)
  private String userId;

  @ApiModelProperty(value = "角色Id", required = false)
  private String roleId;
}
