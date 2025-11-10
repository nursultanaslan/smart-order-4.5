package com.turkcell.notification_service.web;

import com.turkcell.notification_service.application.dto.NotificationRequest;
import com.turkcell.notification_service.service.NotificationProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notify")
public class NotificationsController {
    private static final Logger logger = LoggerFactory.getLogger(NotificationsController.class);
    private final NotificationProducer producer;

    public NotificationsController(NotificationProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        logger.info("Notification request received: {}", request);
        producer.sendNotification(request);
        return ResponseEntity.ok("Notification received");
    }
}
