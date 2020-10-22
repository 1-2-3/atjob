package com.bzb.atjob.app.auth.core.model;

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
import lombok.Data;

@ApiModel(description = "系统角色")
@Data
@TableName(value = "AUTH_ROLE")
public class Role {
  @ApiModelProperty(value = "ROLE_ID", required = false)
  @TableId(type = IdType.ASSIGN_ID)
  private String roleId;

  @ApiModelProperty(value = "角色名称", required = false)
  private String name;

  @ApiModelProperty(value = "角色编码", required = false)
  private String code;

  @ApiModelProperty(value = "是否停用", required = false)
  private Boolean isStop;

  @ApiModelProperty(value = "描述", required = false)
  private String description;

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

  @JsonIgnore
  @TableField(exist = false)
  @ApiModelProperty(value = "拥有的页面", required = false)
  private List<PageOwnedByRole> pagesOwned;

  /**
   * 拥有的页面Id列表.
   *
   * @return
   */
  @ApiModelProperty(value = "拥有的页面Id列表", required = false)
  public List<String> getPageIdListOwned() {
    return this.pagesOwned == null
        ? null
        : this.pagesOwned.stream().map(t -> t.getPageId()).collect(Collectors.toList());
  }

  /**
   * 设置拥有的页面Id列表.
   *
   * @param pageIdList 拥有的页面Id列表
   */
  @ApiModelProperty(value = "拥有的页面Id列表")
  public void setPageIdListOwned(List<String> pageIdList) {
    if (pageIdList == null) {
      return;
    }

    var newPagesOwned =
        pageIdList.stream()
            .map(
                pageId -> PageOwnedByRole.builder().pageId(pageId).roleId(this.getRoleId()).build())
            .collect(Collectors.toList());

    this.setPagesOwned(newPagesOwned);
  }

  /**
   * 设置主键.
   *
   * @param roleId 主键
   */
  public void setRoleId(String roleId) {
    this.roleId = roleId;

    if (this.pagesOwned != null) {
      var newPagesOwned =
          this.pagesOwned.stream()
              .map(
                  t ->
                      PageOwnedByRole.builder()
                          .rolePageId(t.getRolePageId())
                          .pageId(t.getRolePageId())
                          .roleId(roleId)
                          .build())
              .collect(Collectors.toList());

      this.setPagesOwned(newPagesOwned);
    }
  }
}
