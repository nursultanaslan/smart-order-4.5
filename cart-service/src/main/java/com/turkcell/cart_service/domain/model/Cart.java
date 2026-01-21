package com.turkcell.cart_service.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

//rich domain
public class Cart {

    private final CartId id;

    private CustomerId customerId;

    private BigDecimal cartTotalPrice;
    private String currency;
    private OffsetDateTime createdAt;
    private CartStatus status;

    private final List<CartItem> items;

    private Cart(CartId id, CustomerId customerId, BigDecimal cartTotalPrice, String currency, OffsetDateTime createdAt,
            CartStatus status, List<CartItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.cartTotalPrice = cartTotalPrice;
        this.currency = currency;
        this.createdAt = createdAt != null ? createdAt : OffsetDateTime.now();
        this.status = status != null ? status : CartStatus.getDefault();
        this.items = items;
    }

    public static Cart create(CustomerId customerId, BigDecimal cartTotalPrice, String currency,
            OffsetDateTime createdAt, CartStatus status, List<CartItem> items) {
        return new Cart(
                CartId.generate(),
                customerId,
                cartTotalPrice,
                currency,
                createdAt,
                status,
                items);
    }

    public static Cart rehydrate(CartId id, CustomerId customerId, BigDecimal cartTotalPrice, String currency,
            OffsetDateTime createdAt, CartStatus status, List<CartItem> items) {
        return new Cart(
                id,
                customerId,
                cartTotalPrice,
                currency,
                createdAt,
                status,
                items);
    }

    // cart behaviors (business invariants)
    public void addItemToCart(CartItem item) {
        if (this.status == CartStatus.CHECKED_OUT) {
            return;
        }
        items.add(item);
        calculateCartTotalPrice();
    }

    public void removeItemFromCart(UUID productId) {
        items.removeIf(item -> item.productId().equals(productId));
        calculateCartTotalPrice();
    }


    public void selectQuantity(UUID productId, int newQuantity) {
        if (this.status == CartStatus.CHECKED_OUT) {
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            CartItem existingItem = items.get(i);
            if (existingItem.productId().equals(productId)) {
                items.set(i, new CartItem(existingItem.productId(), newQuantity,  existingItem.unitPrice(), existingItem.currency()));
                calculateCartTotalPrice();
                return;
            }
        }
    }

    public void calculateCartTotalPrice() {
        this.cartTotalPrice = items
                .stream()
                .map(CartItem::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    //sipariş tamamlandıktan sonra kafkadan gelen mesajla tetiklenir.
    public void clearCart() {
        if (this.status == CartStatus.CHECKED_OUT){
            this.items.clear();
        }
    }


    // getters
    public CartId id() {
        return id;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public BigDecimal cartTotalPrice() {
        return cartTotalPrice;
    }

    public String currency() {
        return currency;
    }

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    public CartStatus status() {
        return status;
    }

    //unmodifiable -> değişim sadece domain metodları üzerinden mümkün. dısardan değil.
    public List<CartItem> items() {
        return java.util.Collections.unmodifiableList(items);
    }
}
