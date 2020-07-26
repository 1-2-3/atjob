package com.bzb.atjob.app.auth.core.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "系统用户")
@Data
@TableName(value = "AUTH_USER")
public class User {
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

    @ApiModelProperty(value = "创建人Id@AUTH_USER", required = false)
    private String createUserId;

    @ApiModelProperty(value = "修改人id@AUTH_USER", required = false)
    private String modifyUserId;

    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "修改时间", required = false)
    private Date modifyTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "角色Id列表", required = false)
    private String[] roleIds;
}