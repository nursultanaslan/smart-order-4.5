package com.turkcell.cart_service.domain.model;

import java.math.BigDecimal;
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
        for (int i = 0; i < items.size(); i++) {
            CartItem existing = items.get(i);
            if (existing.productId().equals(item.productId())) {
                items.set(i, new CartItem(existing.productId(), existing.quantity() + 1, existing.unitPrice()));
                calculateCartTotalPrice();
                return;
            }
        }
        items.add(item);
        calculateCartTotalPrice();
    }

    public void removeItemFromCart(UUID productId) {
        items.removeIf(item -> item.productId().equals(productId));
        calculateCartTotalPrice();
    }

    public void increaseItemQuantity(CartItem item) {
        for (int i = 0; i < items.size(); i++) {
            CartItem cartItem = items.get(i);
            if (cartItem.productId().equals(item.productId())) {
                items.set(i, new CartItem(cartItem.productId(), cartItem.quantity() + 1, cartItem.unitPrice()));
                break;
            }
        }
        calculateCartTotalPrice();
    }

    public void decreaseItemQuantity(CartItem item) {
        for (int i = 0; i < items.size(); i++) {
            CartItem cartItem = items.get(i);
            if (cartItem.productId().equals(item.productId())) {
                int newQty = cartItem.quantity() - 1;
                if (newQty <= 0) {
                    items.remove(i);
                } else {
                    items.set(i, new CartItem(cartItem.productId(), newQty, cartItem.unitPrice()));
                }
                break;
            }
        }
        calculateCartTotalPrice();
    }

    public void selectQuantity(CartItem item, Integer newQuantity) {
        for (int i = 0; i < items.size(); i++) {
            CartItem cartItem = items.get(i);
            if (cartItem.productId().equals(item.productId())) {
                if (newQuantity <= 0) {
                    items.remove(i);
                } else {
                    items.set(i, new CartItem(cartItem.productId(), newQuantity, cartItem.unitPrice()));
                }
                break;
            }
        }
        calculateCartTotalPrice();
    }

    public void calculatePriceSnapshot(CartItem item) {
        BigDecimal priceSnapshot = item.unitPrice()
                .multiply(BigDecimal.valueOf(item.quantity()));
    }

    public void calculateCartTotalPrice() {
        this.cartTotalPrice = items.stream()
            .map(ci -> ci
                    .unitPrice()
                    .multiply(BigDecimal.valueOf(ci.quantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    //sipariş tamamlandıktan sonra kafkadan gelen mesajla tetiklenir.
    public void clearCart() {
        this.items.clear();
        this.cartTotalPrice = BigDecimal.ZERO;
        this.status = CartStatus.getDefault();
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

    public List<CartItem> items() {
        return items;
    }
}
