package com.turkcell.customer_service.application.dto.request;

import jakarta.validation.constraints.*;

public record CreateCustomerRequest(
        @Email @NotBlank String email,
        String username
        ) {
}
