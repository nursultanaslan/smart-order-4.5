package com.turkcell.notification_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.notification_service.application.dto.NotificationRequest;
import com.turkcell.notification_service.domain.model.NotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {
    private static final Logger logger = LoggerFactory.getLogger(EmailConsumer.class);
    private final ObjectMapper objectMapper;

    public EmailConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "notification-queue", groupId = "email-group")
    public void listen(String message) {
        try {
            NotificationRequest req = objectMapper.readValue(message, NotificationRequest.class);
            if (req.getType() == NotificationType.EMAIL) {
                logger.info("EMAIL Notification received: to={}, subject={}", req.getRecipient(), req.getSubject());
                // Burada gerçek e-posta gönderimi entegre edilecek
            }
        } catch (Exception ex) {
            logger.error("Error processing email message:", ex);
        }
    }
}
