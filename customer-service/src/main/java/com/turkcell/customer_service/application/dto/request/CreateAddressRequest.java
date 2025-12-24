package com.turkcell.customer_service.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAddressRequest(
        @NotBlank String country,
        @NotBlank String city,
        @NotBlank String street,
        @NotBlank @Min(5) String postalCode,
        @NotNull Integer houseNumber
) {
}
