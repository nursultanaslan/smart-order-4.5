package com.turkcell.order_service.domain.aggregate.enums;

public enum OrderStatus {
    APPROVAL_PENDING,   //pending state is an example of a semantic lock countermeasure
    APPROVED,
    REJECTED,
    PREPARING,
    SHIPPED,
    DELIVERED,      //shipment serviceten gelen CARGODELIVERED eventinden sonra Order statusu DELIVERED olarak ayarlanır.
    CANCEL_PENDING,
    CANCELLED,
    RETURN_REQUESTED,
    RETURNED,
    COMPLETED;     //İade süresi doldu herhangi bir iade işlemi yapılmadı -> sipariş tamamlandı olarak işaretlenir.

    public static OrderStatus getDefault() {
        return APPROVAL_PENDING;
    }
}
