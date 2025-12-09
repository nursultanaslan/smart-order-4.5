package com.turkcell.customer_service.application.dto.request;

import java.util.UUID;

public record DeleteCustomerRequest(UUID customerId) {
}
