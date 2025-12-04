package com.turkcell.order_service.application.command;

import com.turkcell.order_service.application.client.ProductClient;
import com.turkcell.order_service.application.dto.response.OrderResponse;
import com.turkcell.order_service.application.dto.response.ProductResponse;
import com.turkcell.order_service.application.mapper.OrderMapper;
import com.turkcell.order_service.core.cqrs.CommandHandler;
import com.turkcell.order_service.domain.event.OrderCreatedEvent;
import com.turkcell.order_service.domain.model.Order;
import com.turkcell.order_service.domain.model.OrderLine;
import com.turkcell.order_service.domain.port.DomainEventsPublisher;
import com.turkcell.order_service.domain.port.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateOrderCommandHandler implements CommandHandler<CreateOrderCommand, OrderResponse> {

    private static final Logger logger = LoggerFactory.getLogger(CreateOrderCommandHandler.class);

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final DomainEventsPublisher domainEventsPublisher;
    private final ProductClient productClient;

    public CreateOrderCommandHandler(OrderRepository orderRepository, OrderMapper orderMapper,
            DomainEventsPublisher domainEventsPublisher, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.domainEventsPublisher = domainEventsPublisher;
        this.productClient = productClient;
    }

    @Override
    @Transactional
    public OrderResponse handle(CreateOrderCommand command) {

        Order order = orderMapper.toDomain(command);

        // Önce stock kontrolü ve azaltma işlemini yapıyoruz
        // Eğer stock yetersizse, order kaydedilmeden işlem başarısız olur
        try {
            for (OrderLine line : order.lines()) {
                ProductResponse productResponse = productClient.decreaseStock(line.productId(), line.quantity());

                // ProductResponse'u kontrol ediyoruz
                if (productResponse == null) {
                    logger.error("Product stock decrease returned null for productId: {}", line.productId());
                    throw new RuntimeException(
                            "Product stock decrease failed: response is null for product " + line.productId());
                }

                // Güncellenmiş stock bilgisini logluyoruz
                logger.debug("Stock decreased for productId: {}, remaining stock: {}",
                        line.productId(), productResponse.stock());
            }
        } catch (Exception e) {
            logger.error("Failed to decrease product stock for order: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update product stock: " + e.getMessage(), e);
        }

        // Stock işlemi başarılı olduktan sonra order'ı kaydediyoruz
        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.id(),
                savedOrder.customerId(),
                savedOrder.cartId(),
                savedOrder.createdAt(),
                savedOrder.totalPrice(),
                savedOrder.currency(),
                savedOrder.lines());

        domainEventsPublisher.publish(event);

        return orderMapper.toResponse(savedOrder);
    }
}
