package com.turkcell.product_service.domain.model;

import java.util.Objects;

public class Product {

    private final ProductId id;
    private String name;
    private Money price;
    private String description;
    private Integer stock;

    private Product(ProductId id, String name, Money money, String description, Integer stock) {
        this.id = id;
        this.name = name;
        this.price = money;
        this.description = description;
        this.stock = stock;
    }

    public static Product create(String name, Money money, String description, Integer stock ) {
        return new Product(
                ProductId.generate(),
                name,
                money,
                description,
                stock
        );
    }

    public static Product rehydrate(ProductId id, String name, Money money, String description, Integer stock) {
        return new Product(
                id,
                name,
                money,
                description,
                stock
        );
    }

    //validations
    public static void validateName(String name){
        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("name cannot be null");
        }
        if (name.length() > 120){
            throw new IllegalArgumentException("Name length cannot be larger than 120 characters");
        }
    }

    public static void validateDescription(String description){
        if (description == null || description.isEmpty()){
            throw new IllegalArgumentException("Description cannot be null");
        }
        if (description.length() >= 255){
            throw new IllegalArgumentException("Description length must be minimum 255 characters");
        }
    }

    public static void validateStock(Integer stock){
        if (stock == null || stock < 0){
            throw new IllegalArgumentException("Stock cannot be null or negative value");
        }
    }

    //worker methods
    public void rename(String name){
        validateName(name);
        this.name = name;
    }

    public void changePrice(Money newPrice){
        Objects.requireNonNull(newPrice, "Price cannot be null");
        this.price = newPrice;
    }

    public void changeDescription(String description){
        validateDescription(description);
        this.description = description;
    }

    //stok azalt
    public void dispatch(Integer quantityToDispatch){
        if (quantityToDispatch == null || quantityToDispatch < 0){
            throw new IllegalArgumentException("Quantity to dispatch must be positive");
        }
        if (this.stock < quantityToDispatch){
            throw new IllegalArgumentException("insufficient stock");
        }

        this.stock -= quantityToDispatch;
    }

    //stok arttır.
    public void restock(Integer quantityToRestock){
        if (quantityToRestock == null || quantityToRestock < 0){
            throw new IllegalArgumentException("Quantity to restock must be positive");
        }

        this.stock += quantityToRestock;
    }

    //getters
    public ProductId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Money price() {
        return price;
    }

    public String description() {
        return description;
    }

    public Integer stock() {
        return stock;
    }
}
