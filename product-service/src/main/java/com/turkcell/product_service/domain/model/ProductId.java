package com.turkcell.product_service.domain.model;

import java.io.Serializable;
import java.util.UUID;

public record ProductId(UUID value) implements Serializable {
}
