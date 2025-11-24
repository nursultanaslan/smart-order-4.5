package com.turkcell.product_service.core.cqrs;

public interface QueryHandler<Q extends Query<R>, R> {

    R handle(Q query);
}
