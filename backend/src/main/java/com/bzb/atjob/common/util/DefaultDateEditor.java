package com.bzb.atjob.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DefaultDateEditor extends java.beans.PropertyEditorSupport {
  /** 日期转换. */
  public void setAsText(String text) throws java.lang.IllegalArgumentException {
    if (text != null && text.length() > 0) {
      if (text.matches("\\d{4}-\\d{1,2}-\\d{1,2}T\\d{2}:\\d{2}:\\d{2}.*")) {
        // 转换格林威治时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setLenient(false);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
          Date d = dateFormat.parse(text);
          this.setValue(d);
        } catch (ParseException e) {
          throw new java.lang.IllegalArgumentException(e.getMessage());
        }
      } else if (text.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{2}:\\d{2}:\\d{2}")) {
        // 普通时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        try {
          Date d = dateFormat.parse(text);
          this.setValue(d);
        } catch (ParseException e) {
          throw new java.lang.IllegalArgumentException(e.getMessage());
        }
      } else if (text.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
        // 普通日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
          Date d = dateFormat.parse(text);
          this.setValue(d);
        } catch (ParseException e) {
          throw new java.lang.IllegalArgumentException(e.getMessage());
        }
      } else {
        super.setAsText(text);
      }
    } else {
      this.setValue(null);
    }
  }
}
