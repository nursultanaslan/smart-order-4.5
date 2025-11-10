package com.turkcell.notification_service.application.dto;

import com.turkcell.notification_service.domain.model.NotificationType;

public class NotificationRequest {
    private String recipient;
    private String subject;
    private String body;
    private NotificationType type;

    // Getters & Setters
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }
}
