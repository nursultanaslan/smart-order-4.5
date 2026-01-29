package com.turkcell.order_service.application.command;

import com.turkcell.order_service.application.client.ProductClient;
import com.turkcell.order_service.application.dto.response.OrderResponse;
import com.turkcell.order_service.application.dto.response.ProductResponse;
import com.turkcell.order_service.application.mapper.OrderMapper;
import com.turkcell.order_service.core.cqrs.CommandHandler;
import com.turkcell.order_service.domain.event.OrderCreatedEvent;
import com.turkcell.order_service.domain.model.Order;
import com.turkcell.order_service.domain.model.OrderItem;
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

        /**
         * Product Servisten -> ürünlerin güncel fiyat ve stok bilgisini al.
         * Eğer stok varsa ürünler rezerve edilir. (stoktan düşülmez)
         * Payment Servise gidilir -> ödeme işlemi başarılı ise PaymentSuccess Eventi fırlatılır.
         * Order Service sipariş durumunu Completed yapar. PENDING to COMPLETED
         * Product Service rezerve stoğu kalıcı olarak düşer.
         * Cart service sepeti boşaltır
         * **/
        try {
            for (OrderItem item : order.items()) {
                ProductResponse productResponse = productClient.decreaseStock(item.productId(), item.quantity());

                // ProductResponse'u kontrol ediyoruz
                if (productResponse == null) {
                    logger.error("Product stock decrease returned null for productId: {}", item.productId());
                    throw new RuntimeException(
                            "Product stock decrease failed: response is null for product " + item.productId());
                }

                // Güncellenmiş stock bilgisini logluyoruz
                logger.debug("Stock decreased for productId: {}, remaining stock: {}",
                        item.productId(), productResponse.stock());
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
                savedOrder.items());

        domainEventsPublisher.publish(event);

        return orderMapper.toResponse(savedOrder);
    }
}
