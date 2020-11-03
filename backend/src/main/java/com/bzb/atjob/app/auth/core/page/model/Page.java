package com.bzb.atjob.app.auth.core.page.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bzb.atjob.common.annotation.AggregateRoot;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@AggregateRoot
@ApiModel(description = "页面")
@Data
@TableName(value = "AUTH_PAGE")
public class Page {

  @ApiModelProperty(value = "PAGE_ID", required = false)
  @TableId(type = IdType.ASSIGN_ID)
  private String pageId;

  @ApiModelProperty(value = "页面名称", required = false)
  private String name;

  @ApiModelProperty(value = "页面编码", required = false)
  private String code;

  @ApiModelProperty(value = "访问路径", required = false)
  private String path;

  @ApiModelProperty(value = "父节ID", required = false)
  private String parent;

  @ApiModelProperty(value = "是否叶", required = false)
  private Boolean isLeaf;

  @ApiModelProperty(value = "ICON", required = false)
  private String icon;

  @ApiModelProperty(value = "是否隐藏", required = false)
  private Boolean isHide;

  @ApiModelProperty(value = "是否禁用", required = false)
  private Boolean isStop;

  @ApiModelProperty(value = "排序", required = false)
  private Integer indexField;

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
}
