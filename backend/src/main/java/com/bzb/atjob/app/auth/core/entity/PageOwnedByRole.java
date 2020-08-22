package com.bzb.atjob.app.auth.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

/** 被角色拥有的页面. */
@Value
@TableName(value = "AUTH_ROLE_PAGE")
public class PageOwnedByRole {
  @ApiModelProperty(value = "主键", required = false)
  @TableId(type = IdType.ASSIGN_ID)
  private String rolePageId;

  @ApiModelProperty(value = "页面Id", required = false)
  private String pageId;

  @ApiModelProperty(value = "角色Id", required = false)
  private String roleId;
}
