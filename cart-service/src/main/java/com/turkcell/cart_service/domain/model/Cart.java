package com.turkcell.cart_service.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

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
        this.createdAt = createdAt;
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

    // sepete ekle + price hesapla
    // sepetten çıkar + price hesapla
    // ürünün adetini azalt + price hesapla
    // ürünün adetini arttır + price hesapla
    // selectQuantity + price hesapla
    // cart behaviors
    public void addItemToCart(CartItem item) {
        // item.productId'si items içerisinde varsa item'in
        // quantity'si 1 arttırılır yoksa yeni item olarak eklensin
        boolean itemExists = false;
        for (int i = 0; i < items.size(); i++) {
            CartItem existingItem = items.get(i);
            if (existingItem.productId().equals(item.productId())) {
                // Aynı productId'ye sahip item bulundu, quantity'yi 1 arttır
                CartItem updatedItem = new CartItem(
                        existingItem.productId(),
                        existingItem.quantity() + 1,
                        existingItem.unitPrice(),
                        existingItem.currency());
                items.set(i, updatedItem);
                itemExists = true;
                calculatePriceSnapshot(updatedItem);
                calculateCartTotalPrice(updatedItem);
                break;
            }
        }

        if (!itemExists) {
            // Yeni item olarak ekle
            items.add(item);
            calculatePriceSnapshot(item);
            calculateCartTotalPrice(item);
        }
    }

    public void removeItemFromCart(CartItem item) {

    }

    public void increaseItemQuantity(CartItem item) {

    }

    public void decreaseItemQuantity(CartItem item) {

    }

    public void selectQuantity(CartItem item, Integer newQuantity) {

    }

    public void calculatePriceSnapshot(CartItem item) {
        BigDecimal priceSnapshot = item.unitPrice()
                .multiply(BigDecimal.valueOf(item.quantity()));
    }

    public void calculateCartTotalPrice(CartItem item) {

    }

    // getters
    public CartId id() {
        return id;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public void setCustomerId(CustomerId customerId) {
        this.customerId = customerId;
    }

    public BigDecimal cartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public String currency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public CartStatus status() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public List<CartItem> items() {
        return items;
    }
}
