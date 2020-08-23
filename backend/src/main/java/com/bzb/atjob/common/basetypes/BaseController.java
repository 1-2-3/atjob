package com.bzb.atjob.common.basetypes;

import com.bzb.atjob.common.util.DefaultDateEditor;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class BaseController {
  @InitBinder
  protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
    this.registerDateEditor(binder); // 注册日期类型解析器
  }

  /** 注册日期类型解析器. */
  private void registerDateEditor(ServletRequestDataBinder binder) {
    binder.registerCustomEditor(Date.class, new DefaultDateEditor());
  }
}
