package com.turkcell.notification_service.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {

    private UUID id;
    private String subject;
    private String body;
    private String recipient; //alıcı
    private NotificationType type;
    private LocalDateTime createdAt;

    private Notification() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }
}
