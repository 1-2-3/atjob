package com.bzb.atjob.common.events;

import java.util.List;

public interface DomainEvents {
  void publish(DomainEvent event);

  default void publish(List<DomainEvent> events) {
    events.forEach(this::publish);
  }
}
