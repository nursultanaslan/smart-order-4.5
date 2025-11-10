package com.turkcell.order_service.domain.port;

import com.turkcell.order_service.domain.model.Order;
import com.turkcell.order_service.domain.model.OrderId;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    List<Order> findAll();
    Optional<Order> findById(OrderId orderId);
    void deleteById(OrderId orderId);
    void delete(Order order);
}
