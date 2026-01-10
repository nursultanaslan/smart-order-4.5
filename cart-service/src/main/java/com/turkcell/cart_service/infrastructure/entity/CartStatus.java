package com.turkcell.cart_service.infrastructure.entity;

public enum CartStatus {
    ACTIVE,
    CHECKED_OUT;

    /**
     * ilk yaratımda ACTIVE
     * odeme yapıldıktan sonra CHECKED_OUT
     * olarak işaretlenir.
     */
    public static CartStatus getDefault() {
        return ACTIVE;
    }
}
