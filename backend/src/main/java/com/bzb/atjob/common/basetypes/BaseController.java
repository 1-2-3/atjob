package com.bzb.atjob.common.basetypes;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.bzb.atjob.common.util.DefaultDateEditor;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class BaseController {
    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        this.RegisterDateEditor(binder); // 注册日期类型解析器
    }

    /**
     * 注册日期类型解析器
     */
    private void RegisterDateEditor(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DefaultDateEditor());
    }
}