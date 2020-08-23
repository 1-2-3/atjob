package com.bzb.atjob.common.vo;

import java.util.List;

/** 分页查询结果. */
public class PaggingResult<T> {
  public PaggingResult() {}

  public PaggingResult(List<T> data, Long total) {
    this.data = data;
    this.total = total;
  }

  /** 列表数据. */
  private List<T> data;

  /** 数据总条数. */
  private Long total;

  public List<T> getData() {
    return this.data;
  }

  public Long getTotal() {
    return this.total;
  }

  public void setData(List<T> data) {
    this.data = data;
  }

  public void setTotal(Long total) {
    this.total = total;
  }
}
