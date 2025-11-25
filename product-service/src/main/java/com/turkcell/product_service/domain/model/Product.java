package com.turkcell.product_service.domain.model;

import java.util.Objects;

public class Product {

    private final ProductId id;
    private String productName;
    private Money price;
    private String brandName;
    private String description;
    private Integer stock;

    private Product(ProductId id, String productName, Money money, String brandName, String description, Integer stock) {
        this.id = id;
        this.productName = productName;
        this.price = money;
        this.brandName = brandName;
        this.description = description;
        this.stock = stock;
    }

    public static Product create(String productName, Money money, String brandName, String description, Integer stock ) {
        return new Product(
                ProductId.generate(),
                productName,
                money,
                brandName,
                description,
                stock
        );
    }

    public static Product rehydrate(ProductId id, String productName, Money money, String brandName, String description, Integer stock) {
        return new Product(
                id,
                productName,
                money,
                brandName,
                description,
                stock
        );
    }

    //validations
    public static void validateProductName(String productName){
        if (productName == null || productName.trim().isEmpty()){
            throw new IllegalArgumentException("name cannot be null");
        }
        if (productName.length() > 120){
            throw new IllegalArgumentException("Name length cannot be larger than 120 characters");
        }
    }

    public static void validateDescription(String description){
        if (description == null || description.trim().isEmpty()){
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
    public void changePrice(Money newPrice){
        Objects.requireNonNull(newPrice, "Price cannot be null");
        this.price = newPrice;
    }

    public void updateProduct(String productName, String description){
        validateProductName(productName);
        validateDescription(description);
        this.productName = productName.trim();
        this.description = description.trim();
    }

    public void updateStock(Integer newStock){
        validateStock(newStock);
        this.stock = newStock;
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

    public String productName() {
        return productName;
    }

    public Money price() {
        return price;
    }

    public String brandName() {
        return brandName;
    }

    public String description() {
        return description;
    }

    public Integer stock() {
        return stock;
    }
}
