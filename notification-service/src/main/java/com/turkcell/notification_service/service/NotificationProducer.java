package com.turkcell.notification_service.service;

import com.turkcell.notification_service.application.dto.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {
    private static final Logger logger = LoggerFactory.getLogger(NotificationProducer.class);
    private static final String BINDING_NAME = "notificationProducer-out-0";
    private final StreamBridge streamBridge;

    @Autowired
    public NotificationProducer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void sendNotification(NotificationRequest request) {
        boolean result = streamBridge.send(BINDING_NAME, request);
        logger.info("Notification sent via StreamBridge (success={}): {}", result, request);
    }
}
