package com.turkcell.order_service.domain.event.base;

import java.util.ArrayList;
import java.util.List;

public class ResultWithDomainEvents <A, E extends DomainEvent>{

    public final A result;
    public final List<E> events;

    public ResultWithDomainEvents(A result, List<E> events) {
        this.result = result;
        this.events = events;
    }

    public ResultWithDomainEvents(A result, E event) {
        this.result = result;
        this.events = new ArrayList<>();
    }
}
