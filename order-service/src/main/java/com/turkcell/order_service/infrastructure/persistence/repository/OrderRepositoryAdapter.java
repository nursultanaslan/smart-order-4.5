package com.turkcell.order_service.infrastructure.persistence.repository;

import com.turkcell.order_service.domain.model.Order;
import com.turkcell.order_service.domain.model.OrderId;
import com.turkcell.order_service.domain.port.OrderRepository;

import java.util.List;
import java.util.Optional;

public class OrderRepositoryAdapter implements OrderRepository {
    @Override
    public Order save(Order order) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return List.of();
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return Optional.empty();
    }

    @Override
    public void deleteById(OrderId orderId) {

    }

    @Override
    public void delete(Order order) {

    }
}
