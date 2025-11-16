package com.turkcell.notification_service.infrastructure.messaging.consumer;

import com.turkcell.notification_service.infrastructure.messaging.event.OrderCreatedEvent;
import com.turkcell.notification_service.infrastructure.messaging.idempotency.ProcessedMessage;
import com.turkcell.notification_service.infrastructure.messaging.idempotency.ProcessedMessageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.UUID;
import java.util.function.Consumer;

@Configuration
public class OrderCreatedConsumer {

    private final ProcessedMessageRepository processedMessageRepository;
    private static final UUID CONSUMER_ID = UUID.fromString("00000000-0000-0000-0000-000000000001"); // Notification
                                                                                                     // service consumer
                                                                                                     // ID

    public OrderCreatedConsumer(ProcessedMessageRepository processedMessageRepository) {
        this.processedMessageRepository = processedMessageRepository;
    }

    @Bean
    public Consumer<Message<OrderCreatedEvent>> orderCreated() {
        return message -> {
            OrderCreatedEvent event = message.getPayload();

            // Kafka header'larından eventId/messageId'yi al
            UUID eventId = extractEventId(message);

            // Idempotency kontrolü
            if (isAlreadyProcessed(eventId)) {
                System.out.println("Message already processed. Skipping. EventId: " + eventId);
                return;
            }

            try {
                // Mesajı işle
                processOrderCreated(event);

                // İşlendi olarak işaretle
                markAsProcessed(eventId);

            } catch (Exception e) {
                System.err.println("Error processing order created event: " + e.getMessage());
                throw e;
            }
        };
    }

    private UUID extractEventId(Message<OrderCreatedEvent> message) {
        // Spring Cloud Stream'de eventId genellikle header'larda olur
        // Önce "eventId" header'ını kontrol et, yoksa event'in orderId'sini kullan
        Object eventIdHeader = message.getHeaders().get("eventId");
        if (eventIdHeader instanceof UUID) {
            return (UUID) eventIdHeader;
        }

        // Eğer header'da yoksa, messageId header'ını dene
        Object messageIdHeader = message.getHeaders().get("messageId");
        if (messageIdHeader instanceof UUID) {
            return (UUID) messageIdHeader;
        }

        // Son çare olarak orderId'yi eventId olarak kullan
        // Not: Bu ideal değil, order service'de eventId'yi header'a eklemek daha iyi
        // olur
        return message.getPayload().orderId();
    }

    private boolean isAlreadyProcessed(UUID eventId) {
        return processedMessageRepository
                .findByMessageIdAndConsumerId(eventId, CONSUMER_ID)
                .isPresent();
    }

    private void markAsProcessed(UUID eventId) {
        ProcessedMessage processedMessage = new ProcessedMessage(
                UUID.randomUUID(),
                eventId,
                CONSUMER_ID);
        processedMessageRepository.save(processedMessage);
    }

    private void processOrderCreated(OrderCreatedEvent event) {
        System.out.println("Processing order created event");
        System.out.println("Order ID: " + event.orderId());
        System.out.println("Customer ID: " + event.customerId());
        System.out.println("Total Price: " + event.totalPrice() + " " + event.currency());
        System.out.println("Order Lines: " + event.lines().size());

        // Burada gerçek bildirim gönderme işlemi yapılacak
        System.out.println("Send mail to customer: " + event.customerId());
        System.out.println("Send SMS to customer: " + event.customerId());
    }
}
