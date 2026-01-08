package com.turkcell.product_service.infrastructure.persistence.outbox;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "outbox", indexes = {
        @Index(name = "ix_outbox_event_id", columnList = "eventId", unique = true),
        @Index(name = "ix_outbox_status_created", columnList = "status, createdAt")
})
public class ProductOutboxEntity {

    @Id
    @Column(nullable = false, columnDefinition = "uuid")
    private UUID id = UUID.randomUUID();

    @Column(nullable = false, columnDefinition = "uuid")
    private UUID eventId = UUID.randomUUID();
    @Column(name = "event_type")
    private String eventType;
    @Column(name = "payload_json")
    private String payloadJson;

    @Column(nullable = false, columnDefinition = "uuid")
    private UUID aggregateId;
    @Column(name = "aggregate_type")
    private String aggregateType;

    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductOutboxStatus status = ProductOutboxStatus.PENDING;
    @Column(name = "retry_count", nullable = false)
    private Integer retryCount = 0;

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID eventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public String eventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String payloadJson() {
        return payloadJson;
    }

    public void setPayloadJson(String payloadJson) {
        this.payloadJson = payloadJson;
    }

    public UUID aggregateId() {
        return aggregateId;
    }

    public void setAggregateId(UUID aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String aggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime updatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ProductOutboxStatus status() {
        return status;
    }

    public void setStatus(ProductOutboxStatus status) {
        this.status = status;
    }

    public Integer retryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }
}
