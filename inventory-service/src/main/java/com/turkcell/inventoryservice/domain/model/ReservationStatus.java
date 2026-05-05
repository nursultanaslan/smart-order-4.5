package com.turkcell.inventoryservice.domain.model;

public enum ReservationStatus {
    /**
     * Initial state -> reservation created and stock is reserved
     */
    PENDING,
    /**
     * Reservation confirmed - order payment successful
     */
    CONFIRMED,
    /**
     * Reservation canceled - order canceled, stock returned
     */
    CANCELLED,
    /**
     * Reservation expired due to TTL timeout
     */
    EXPIRED;

    public static ReservationStatus getDefault() {
        return PENDING;
    }
}
