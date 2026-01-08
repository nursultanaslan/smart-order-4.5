package com.turkcell.order_service.infrastructure.persistence.entity.outbox;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "outbox", indexes = {
        @Index(name = "ix_outbox_event_id", columnList = "eventId", unique = true), //aynı eventin tekrar üretimini engeller
        @Index(name = "ix_outbox_status_created", columnList = "status, createdAt") // status ve createdAt alanları
                                                                                    // öncelikli sıralama (performans
                                                                                    // icin)
})
public class OrderOutboxEntity {

    @Id
    @Column(nullable = false, columnDefinition = "uuid")
    private UUID id = UUID.randomUUID(); // outbox mesajın kendi id'si.

    @Column(nullable = false, columnDefinition = "uuid")
    private UUID eventId = UUID.randomUUID(); // kafkada tutulacak her evente ozel id. (idempotency)
    private String eventType; // hangi türde event gidecek -> OrderCreatedEvent
    private String payloadJson; // gönderilecek eventin içerisindeki Json detayları

    @Column(nullable = false, columnDefinition = "uuid")
    private UUID aggregateId; // aggregate'in id'si (orderId)
    private String aggregateType; // kafkaya gönderdiğimiz eventin domain nesnesi (ornegin: order)

    private OffsetDateTime createdAt = OffsetDateTime.now(); // mesaj ne zaman oluşturuldu?
    private OffsetDateTime updatedAt; // son işlem yapılan tarihi tutarız (null olabilir, başlangıçta da null
                                      // olacaktır)

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderOutboxStatus status = OrderOutboxStatus.PENDING; // (pending olarak başlar)

    private Integer retryCount = 0; // bu mesajı kaç kere göndermeyi denedim? (0'dan başlar)

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

    public String aggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public UUID aggregateId() {
        return aggregateId;
    }

    public void setAggregateId(UUID aggregateId) {
        this.aggregateId = aggregateId;
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

    public OrderOutboxStatus status() {
        return status;
    }

    public void setStatus(OrderOutboxStatus status) {
        this.status = status;
    }

    public Integer retryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
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
}
