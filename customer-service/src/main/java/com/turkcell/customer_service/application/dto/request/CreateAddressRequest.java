package com.turkcell.customer_service.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateAddressRequest(
        @NotNull UUID customerId,
        @NotBlank String country,
        @NotBlank String city,
        @NotBlank String street,
        @NotBlank @Min(5) String postalCode,
        @NotNull Integer houseNumber
) {
}
