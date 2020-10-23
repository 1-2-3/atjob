package com.bzb.atjob.app.auth.core.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@ApiModel(description = "部门")
@Data
@TableName(value = "AUTH_DEPT")
public class Dept {
  @ApiModelProperty(value = "DEPT_ID", required = false)
  @TableId(type = IdType.ASSIGN_ID)
  private String deptId;

  @ApiModelProperty(value = "部门名称", required = false)
  private String name;

  @ApiModelProperty(value = "部门编码", required = false)
  private String code;

  @ApiModelProperty(value = "父节ID", required = false)
  private String parent;

  @ApiModelProperty(value = "录入码", required = false)
  private String inputCode;

  @ApiModelProperty(value = "排序", required = false)
  private Integer indexField;

  @ApiModelProperty(value = "描述", required = false)
  private String description;

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
}
