package com.turkcell.notification_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/delivery-logs")
public class DeliveryLogController {
    @GetMapping
    public List<Map<String, String>> getLogs() {
        // Örnek statik data
        return List.of(
                Map.of("id", "1", "type", "EMAIL", "status", "DELIVERED", "recipient", "mail@example.com"),
                Map.of("id", "2", "type", "SMS", "status", "FAILED", "recipient", "+905555555555")
        );
    }
}
