package com.turkcell.order_service.domain.aggregate.valueobjects;

import java.util.UUID;
import java.util.regex.Pattern;

public record OrderNumber(String value) {

    private static final Pattern PATTERN = Pattern.compile("^O[A-Z0-9]{8}$");

    public OrderNumber {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Value cannot be null or blank");
        }
        if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid order number");
        }
    }

    public static OrderNumber generate() {
        return new OrderNumber(
                "O" + UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 8)
                        .toUpperCase()
        );
    }

    /*
      TODO: OrderNumberGenerator için ayrı class oluşturulacak.
      orderNumber generate etme sorumluluğu bu classa taşınacak.
      sequenceNumber'ı Redis ile al.
     */
}
