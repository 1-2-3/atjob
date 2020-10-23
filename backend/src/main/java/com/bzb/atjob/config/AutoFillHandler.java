package com.bzb.atjob.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.bzb.atjob.common.auth.JwtUtil;

// import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

/** 自动填充. */
// @Slf4j
@Component
public class AutoFillHandler implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    // log.info("start insert fill ....");
    String token = SecurityUtils.getSubject().getPrincipal().toString();
    String userid = JwtUtil.getUserid(token);
    this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
    this.strictInsertFill(metaObject, "createUserId", String.class, userid);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    // log.info("start update fill ....");
    String token = SecurityUtils.getSubject().getPrincipal().toString();
    String userid = JwtUtil.getUserid(token);
    this.strictUpdateFill(metaObject, "modifyTime", Date.class, new Date());
    this.strictUpdateFill(metaObject, "modifyUserId", String.class, userid);

  }
}
