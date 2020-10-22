package com.bzb.atjob.common.events;

import java.time.Instant;

public interface DomainEvent {
  String getEventId();

  String getAggregateId();

  Instant getWhen();
}
