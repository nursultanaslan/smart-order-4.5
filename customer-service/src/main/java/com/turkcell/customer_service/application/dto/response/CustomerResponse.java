package com.turkcell.customer_service.application.dto.response;

import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String firstName,
        String lastName
) {
}
