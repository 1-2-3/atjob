package com.bzb.atjob.app.auth.core.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bzb.atjob.app.auth.core.entity.RoleOwnedByUser;
import com.bzb.atjob.app.auth.core.entity.User;
import com.bzb.atjob.app.auth.core.mapper.RoleOwnedByUserMapper;
import com.bzb.atjob.app.auth.core.mapper.UserMapper;
import com.bzb.atjob.common.util.MybatisUtil;
import com.bzb.atjob.common.vo.PaggingResult;
import java.util.List;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRepository {

  private final UserMapper userMapper;
  private final RoleOwnedByUserMapper roleOwnedByUserMapper;

  /**
   * 获取匹配主键的用户.
   *
   * @param id 主键
   * @return
   */
  public User byId(String id) {
    User entity = userMapper.selectById(id);

    var rolesOwend =
        roleOwnedByUserMapper.selectList(
            new LambdaQueryWrapper<RoleOwnedByUser>().eq(RoleOwnedByUser::getUserId, id));
    entity.setRolesOwned(rolesOwend);

    return entity;
  }

  /**
   * 保存或更新用户.
   *
   * @param entity 用户实体
   */
  public void save(User entity) {
    // 验证编码不允许重复
    if (this.isUserExists(entity.getUserId(), entity.getCode())) {
      throw new ValidationException("编码不允许重复！");
    }

    if (StringUtils.isBlank(entity.getUserId())) {
      if (StringUtils.isBlank(entity.getPwd())) {
        entity.resetToDefaultPwd(); // 创建用户时设置默认密码
      }

      userMapper.insert(entity);
      saveRolesOwned(entity.getUserId(), entity.getRolesOwned());
    } else {
      userMapper.updateById(entity);
      saveRolesOwned(entity.getUserId(), entity.getRolesOwned());
    }
  }

  /** 获取匹配登录名的系统用户。找不到时返回 null. */
  public User getByLoginName(String loginName) {
    List<User> list =
        userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getLoginName, loginName));

    if (list.size() > 0) {
      User entity = list.get(0);
      var rolesOwend =
          roleOwnedByUserMapper.selectList(
              new LambdaQueryWrapper<RoleOwnedByUser>()
                  .eq(RoleOwnedByUser::getUserId, entity.getUserId()));
      entity.setRolesOwned(rolesOwend);

      return entity;
    } else {
      return null;
    }
  }

  private void saveRolesOwned(String userId, List<RoleOwnedByUser> rolesOwned) {
    roleOwnedByUserMapper.delete(
        new LambdaQueryWrapper<RoleOwnedByUser>().eq(RoleOwnedByUser::getUserId, userId));
    if (rolesOwned != null) {
      for (RoleOwnedByUser roleOwnedByUser : rolesOwned) {
        roleOwnedByUserMapper.insert(roleOwnedByUser);
      }
    }
  }

  /**
   * 删除用户.
   *
   * @param id 用户Id
   */
  public void delete(String id) {
    userMapper.deleteById(id);
  }

  /**
   * 获取用户列表.
   *
   * @param pageNum 页码
   * @param pageSize 每页条数
   * @param sort 排序
   * @param query 模糊查询字符串
   */
  public PaggingResult<User> getUserList(Long pageNum, Long pageSize, String sort, String query) {
    Page<User> page = MybatisUtil.createPage(pageNum, pageSize);

    // 排序
    MybatisUtil.getOrderItemList(sort, User.class, true).stream().forEach(page::addOrder);

    // 筛选
    QueryWrapper<User> wrapper = new QueryWrapper<User>();
    wrapper
        .lambda()
        .nested(
            w ->
                w.like(User::getName, query)
                    .or()
                    .like(User::getCode, StringUtils.defaultString(query)));

    userMapper.selectPage(page, wrapper);

    return new PaggingResult<User>(page.getRecords(), page.getSize());
  }

  /**
   * 判断用户信息是否已存在.
   *
   * @param userId 用户Id
   * @return
   */
  private boolean isUserExists(String userId, String code) {
    if (StringUtils.isBlank(userId)) {
      return userMapper.selectCount(new QueryWrapper<User>().lambda().eq(User::getCode, code)) > 0;
    } else {
      return userMapper.selectCount(
              new QueryWrapper<User>().lambda().ne(User::getUserId, userId).eq(User::getCode, code))
          > 0;
    }
  }
}
