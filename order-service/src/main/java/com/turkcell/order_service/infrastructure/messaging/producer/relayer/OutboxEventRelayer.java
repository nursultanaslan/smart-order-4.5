package com.turkcell.order_service.infrastructure.messaging.producer.relayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.order_service.infrastructure.messaging.producer.event.OrderCreatedIntegrationEvent;
import com.turkcell.order_service.infrastructure.persistence.entity.outbox.OrderOutboxEntity;
import com.turkcell.order_service.infrastructure.persistence.entity.outbox.OrderOutboxStatus;
import com.turkcell.order_service.infrastructure.persistence.repository.OutboxRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class OutboxEventRelayer {

    private final OutboxRepository outboxRepository;
    private final StreamBridge streamBridge;
    private final ObjectMapper objectMapper;

    public OutboxEventRelayer(OutboxRepository outboxRepository, StreamBridge streamBridge, ObjectMapper objectMapper) {
        this.outboxRepository = outboxRepository;
        this.streamBridge = streamBridge;
        this.objectMapper = objectMapper;
    }

    //bekleyen eventleri publish et.
    //scheduled -> planlanmış fonk.
    @Scheduled(fixedRate = 5000)
    public void publishPendingEvent() throws JsonProcessingException {
        System.out.println("publish pending event is called");

        //OutboxStatusu pending olan eventleri bul pendingEventse at
        List<OrderOutboxEntity> pendingEvents =
                outboxRepository.findByStatusOrderByCreatedAtAsc(OrderOutboxStatus.PENDING);

        for (OrderOutboxEntity pendingEvent : pendingEvents) {
            //Deserialize
            //db'den okurken tekrardan event türüne cevirip okuyoruz.
            OrderCreatedIntegrationEvent event =
                    objectMapper.readValue(pendingEvent.payloadJson(), OrderCreatedIntegrationEvent.class);

            Message<OrderCreatedIntegrationEvent> message = MessageBuilder.withPayload(event).build();
            //Kafkaya ulaştı
            try {
                boolean isSent = streamBridge.send("OrderCreatedEvent", message);
                //fakat isSent'in true olması -> mesaj gitmedi
                if (!isSent) {
                    pendingEvent.setRetryCount(pendingEvent.retryCount() + 1);
                    if (pendingEvent.retryCount() > 5) {
                        pendingEvent.setStatus(OrderOutboxStatus.FAILED);
                    }
                }else {
                    pendingEvent.setStatus(OrderOutboxStatus.SENT);
                }

                pendingEvent.setUpdatedAt(OffsetDateTime.now());
                outboxRepository.save(pendingEvent);

            //kafkaya ulasamadı hata fırlattı -> mesaj gitmedi
            }catch (Exception e) {
                pendingEvent.setRetryCount(pendingEvent.retryCount() + 1);
                if (pendingEvent.retryCount() > 5) {
                    pendingEvent.setStatus(OrderOutboxStatus.FAILED);
                    pendingEvent.setUpdatedAt(OffsetDateTime.now());
                }
                outboxRepository.save(pendingEvent);
            }
        }
    }
}
