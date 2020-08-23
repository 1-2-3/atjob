package com.bzb.atjob.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
// import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/** 自动填充. */
// @Slf4j
@Component
public class AutoFillHandler implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    // log.info("start insert fill ....");
    this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    // log.info("start update fill ....");
    // this.strictUpdateFill(metaObject, "operator", String.class, "Tom");
    this.strictUpdateFill(metaObject, "modifyTime", Date.class, new Date());
  }
}
