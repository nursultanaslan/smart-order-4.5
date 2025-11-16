package com.turkcell.order_service.infrastructure.messaging.producer.relayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.order_service.infrastructure.persistence.entity.OrderOutboxEntity;
import com.turkcell.order_service.infrastructure.persistence.entity.OrderOutboxStatus;
import com.turkcell.order_service.domain.event.OrderCreatedEvent;
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
        List<OrderOutboxEntity> pendingEvents = outboxRepository.findByStatusOrderByCreatedAtAsc(OrderOutboxStatus.PENDING);

        for (OrderOutboxEntity pendingEvent : pendingEvents) {
            //Deserialize
            //db'den okurken tekrardan event türüne cevirip okuyoruz.
            OrderCreatedEvent event = objectMapper.readValue(pendingEvent.payloadJson(), OrderCreatedEvent.class);

            Message<OrderCreatedEvent> message = MessageBuilder.withPayload(event).build();

            try { //Kafkaya ulaştı
                boolean isSent = streamBridge.send("OrderCreatedEvent", message);
                if (!isSent) {  //fakat isSent'in true olması -> mesaj gitmedi
                    pendingEvent.setRetryCount(pendingEvent.retryCount() + 1);
                    if (pendingEvent.retryCount() > 5) {
                        pendingEvent.setStatus(OrderOutboxStatus.FAILED);
                    }
                }else {
                    pendingEvent.setStatus(OrderOutboxStatus.SENT);
                }
                pendingEvent.setUpdatedAt(OffsetDateTime.now());
                outboxRepository.save(pendingEvent);
            }catch (Exception e) { //kafkaya ulasamadı hata fırlattı -> mesaj gitmedi
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
