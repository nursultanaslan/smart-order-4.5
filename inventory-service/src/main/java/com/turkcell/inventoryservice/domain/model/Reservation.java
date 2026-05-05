package com.turkcell.inventoryservice.domain.model;


import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reservation {

    private final ReservationId reservationId;
    private OrderId orderId;

    private List<ReservationItem> items;

    private ReservationStatus status;

    private Instant createdAt;
    private Instant expiresAt;


    private Reservation(ReservationId reservationId, OrderId orderId, List<ReservationItem> items,
                        ReservationStatus status, Instant createdAt, Instant expiresAt) {
        this.reservationId = reservationId;
        this.orderId = orderId;
        this.items = new ArrayList<>(items);
        this.status = status != null ? status : ReservationStatus.getDefault();
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public static Reservation create(OrderId orderId, List<ReservationItem> items) {
        return new Reservation(
                ReservationId.generate(),
                orderId,
                items,
                ReservationStatus.getDefault(),
                Instant.now(),
                Instant.now()
        );
    }

    public static Reservation rehydrate(ReservationId reservationId, OrderId orderId, List<ReservationItem> items,
                                        ReservationStatus status, Instant createdAt, Instant expiresAt) {
        return new Reservation(
                reservationId,
                orderId,
                items,
                status,
                createdAt,
                expiresAt
        );
    }


    public ReservationId reservationId() {
        return reservationId;
    }

    public OrderId orderId() {
        return orderId;
    }

    public List<ReservationItem> items() {
        return Collections.unmodifiableList(items);
    }

    public ReservationStatus status() {
        return status;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant expiresAt() {
        return expiresAt;
    }
}
