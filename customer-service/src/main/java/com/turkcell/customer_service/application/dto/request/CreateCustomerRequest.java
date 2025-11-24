package com.turkcell.customer_service.application.dto.request;

import jakarta.validation.constraints.*;

public record CreateCustomerRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email @NotBlank String email,
        @Pattern(regexp = "^(\\+90|90|0)?5\\d{9}$") @NotBlank String phoneNumber,
        @NotBlank String country,
        @NotBlank String city,
        @NotBlank String street,
        @NotBlank @Min(5) String postalCode,
        @NotNull Integer houseNumber

) {
}
