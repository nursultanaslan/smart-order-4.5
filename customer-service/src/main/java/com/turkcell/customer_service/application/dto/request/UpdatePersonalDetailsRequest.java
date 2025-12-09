package com.turkcell.customer_service.application.dto.request;

import java.util.UUID;

public record UpdatePersonalDetailsRequest(
        UUID customerId,
        String firstName,
        String lastName,
        String email,
        String phone
) {
}
