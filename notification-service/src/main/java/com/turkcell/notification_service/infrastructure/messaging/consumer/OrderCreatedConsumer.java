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

    public OrderCreatedConsumer(ProcessedMessageRepository processedMessageRepository) {
        this.processedMessageRepository = processedMessageRepository;
    }

    @Bean
    public Consumer<Message<OrderCreatedEvent>> orderCreated() {
        return message -> {
            // Kafka header'larından eventId/messageId'yi al
            UUID eventId = extractEventId(message);
            OrderCreatedEvent event = message.getPayload();

            if (processedMessageRepository.existsByEventId(eventId)) {
                System.out.println("Message already processed. Skipping. EventId: " + eventId);
                return;
            }

            try {
                // Mesajı işle
                System.out.println("Processing order created event");
                System.out.println("Order ID: " + event.orderId());
                System.out.println("Customer ID: " + event.customerId());
                System.out.println("Total Price: " + event.totalPrice() + " " + event.currency());
                System.out.println("Order Lines: " + event.lines().size());

                // Burada gerçek bildirim gönderme işlemi yapılacak
                System.out.println("Send mail to customer: " + event.customerId());
                System.out.println("Send SMS to customer: " + event.customerId());

                // İşlendi olarak işaretle
                ProcessedMessage processedMessage = new ProcessedMessage(
                        UUID.randomUUID(),
                        eventId);
                processedMessageRepository.save(processedMessage);

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
        return (UUID) eventIdHeader;
    }
}
