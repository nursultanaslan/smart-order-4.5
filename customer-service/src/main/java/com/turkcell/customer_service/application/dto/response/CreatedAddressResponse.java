package com.turkcell.customer_service.application.dto.response;

public record CreatedAddressResponse(
        String country,
        String city,
        String street,
        String postalCode,
        Integer houseNumber
) {
}
