package com.turkcell.order_service.domain.event.base;

import java.time.Instant;
import java.util.UUID;
//Event objesini ve Event metadatasını tutan generic class.
public class DomainEventEnvelope <T extends DomainEvent>{

    private UUID eventId;
    private UUID aggregateId;
    private String aggregateType;
    private Instant timestamp;
    private T event;

}
