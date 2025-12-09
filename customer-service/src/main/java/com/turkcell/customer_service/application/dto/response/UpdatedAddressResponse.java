package com.turkcell.customer_service.application.dto.response;

public record UpdatedAddressResponse(
        String country,
        String city,
        String street,
        String postalCode,
        Integer houseNumber
) {
}
