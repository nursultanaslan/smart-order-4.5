package com.turkcell.notification_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.notification_service.application.dto.NotificationRequest;
import com.turkcell.notification_service.domain.model.NotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SmsConsumer {
    private static final Logger logger = LoggerFactory.getLogger(SmsConsumer.class);
    private final ObjectMapper objectMapper;

    public SmsConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "notification-queue", groupId = "sms-group")
    public void listen(String message) {
        try {
            NotificationRequest req = objectMapper.readValue(message, NotificationRequest.class);
            if (req.getType() == NotificationType.SMS) {
                logger.info("SMS Notification received: to={}, body={}", req.getRecipient(), req.getBody());
                // Burada gerçek SMS gönderimi entegre edilecek
            }
        } catch (Exception ex) {
            logger.error("Error processing SMS message:", ex);
        }
    }
}
