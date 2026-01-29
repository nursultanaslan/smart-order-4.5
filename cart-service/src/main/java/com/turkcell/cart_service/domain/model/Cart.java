package com.turkcell.cart_service.domain.model;

import com.turkcell.cart_service.domain.exception.InsufficientStockException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//rich domain
public class Cart {

    private final CartId id;

    private final CustomerId customerId;

    private BigDecimal cartTotalPrice;
    private final String currency;
    private final OffsetDateTime createdAt;
    private final CartStatus status;

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
                BigDecimal.ZERO,
                currency,
                OffsetDateTime.now(),
                CartStatus.getDefault(),
                new ArrayList<>());
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

    public void addItem(UUID productId, int quantity, BigDecimal unitPrice, int availableStock) {
        validateStock(quantity, availableStock);
        //aynı üründen sepette varsa miktarı güncelle, yoksa yeni ekle.
        items.stream()
                .filter(item -> item.productId().equals(productId))
                .findFirst()
                .ifPresentOrElse(
                        existingItem -> updateExistingItemQuantity(existingItem, quantity),
                        () -> items.add(new CartItem(productId, quantity, unitPrice, currency))
                );

        calculateCartTotalPrice();
    }

    private void updateExistingItemQuantity(CartItem existingItem, int additionalQuantity) {
        int index = items.indexOf(existingItem);
        items.set(index, new CartItem(
                existingItem.productId(),
                existingItem.quantity() + additionalQuantity,
                existingItem.unitPrice(),
                existingItem.currency()
        ));
    }

    public void removeItemFromCart(UUID productId) {
        items.removeIf(item -> item.productId().equals(productId));
        calculateCartTotalPrice();
    }


    public void updateQuantity(UUID productId, int newQuantity, int availableStock) {
        validateStock(newQuantity, availableStock);
        if (this.status == CartStatus.CHECKED_OUT) {
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            CartItem existingItem = items.get(i);
            if (existingItem.productId().equals(productId)) {
                items.set(i, new CartItem(existingItem.productId(), newQuantity, existingItem.unitPrice(), existingItem.currency()));
                calculateCartTotalPrice();
                return;
            }
        }
    }

    public void calculateCartTotalPrice() {
        this.cartTotalPrice = items
                .stream()
                .map(CartItem::itemTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    //sipariş tamamlandıktan sonra kafkadan gelen mesajla tetiklenir.
    //ödeme başarılı ise sepeti boşalt.
    public void clearCart() {
        if (this.status == CartStatus.CHECKED_OUT) {
            this.items.clear();
        }
    }

    private void validateStock(int quantity, int availableStock) {
        //sepete eklenecek ürün miktari mevcut stoktan küçükse yetersiz stok hatası ver.
        if(quantity > availableStock) {
            throw new InsufficientStockException("insufficient stock");
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
