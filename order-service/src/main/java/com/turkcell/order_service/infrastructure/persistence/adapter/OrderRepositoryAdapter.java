package com.turkcell.order_service.infrastructure.persistence.adapter;

import com.turkcell.order_service.domain.model.Order;
import com.turkcell.order_service.domain.model.OrderId;
import com.turkcell.order_service.domain.repository.IOrderRepository;
import com.turkcell.order_service.infrastructure.persistence.entity.order.JpaOrderEntity;
import com.turkcell.order_service.infrastructure.persistence.mapper.OrderEntityMapper;
import com.turkcell.order_service.infrastructure.persistence.repository.JpaOrderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryAdapter implements IOrderRepository {

    private final JpaOrderRepository orderRepository;
    private final OrderEntityMapper orderMapper;

    public OrderRepositoryAdapter(JpaOrderRepository orderRepository, OrderEntityMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public Order save(Order order) {
        JpaOrderEntity entity = orderMapper.toEntity(order);
        orderRepository.save(entity);
        return orderMapper.toDomain(entity);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository
                .findAll()
                .stream()
                .map(orderMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return orderRepository
                .findById(orderId.value())
                .map(orderMapper::toDomain);
    }

    @Override
    public void deleteById(OrderId orderId) {
        orderRepository.deleteById(orderId.value());

    }

    @Override
    public void delete(Order order) {
        JpaOrderEntity entity = orderMapper.toEntity(order);
        orderRepository.delete(entity);
    }
}
