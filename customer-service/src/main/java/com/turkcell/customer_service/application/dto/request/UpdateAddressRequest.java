package com.turkcell.customer_service.application.dto.request;

import java.util.UUID;

public record UpdateAddressRequest(
        UUID customerId,
        String country,
        String city,
        String street,
        String postalCode,
        Integer houseNumber
) {
}
