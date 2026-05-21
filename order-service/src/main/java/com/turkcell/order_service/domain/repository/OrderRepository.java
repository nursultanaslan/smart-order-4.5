package com.turkcell.order_service.domain.repository;

import com.turkcell.order_service.domain.aggregate.root.Order;
import com.turkcell.order_service.domain.aggregate.valueobjects.OrderId;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    List<Order> findAll();
    Optional<Order> findById(OrderId orderId);
    void deleteById(OrderId orderId);
    void delete(Order order);
}
