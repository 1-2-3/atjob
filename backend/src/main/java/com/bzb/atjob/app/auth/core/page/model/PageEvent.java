package com.bzb.atjob.app.auth.core.page.model;

import com.bzb.atjob.common.events.DomainEvent;
import com.bzb.atjob.common.util.MybatisUtil;
import java.time.Instant;
import lombok.NonNull;
import lombok.Value;

/** 页面相关领域事件. */
public interface PageEvent extends DomainEvent {
  String getPageId();

  default String getAggregateId() {
    return getPageId();
  }

  @Value
  class PageDeleted implements PageEvent {
    @NonNull String eventId = MybatisUtil.nextId().toString();
    @NonNull Instant when;
    @NonNull String pageId;

    public static PageDeleted now(String pageId) {
      return new PageDeleted(Instant.now(), pageId);
    }
  }
}
