package com.turkcell.customer_service.application.dto.response;

public record UpdatedPersonalDetailsResponse(
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
}
