package com.turkcell.product_service.domain.model.product;

import com.turkcell.product_service.domain.exception.InsufficientStockException;
import com.turkcell.product_service.domain.exception.InvalidDescriptionException;
import com.turkcell.product_service.domain.exception.InvalidProductNameException;
import com.turkcell.product_service.domain.exception.InvalidStockException;
import com.turkcell.product_service.domain.model.brand.BrandId;
import com.turkcell.product_service.domain.model.category.CategoryId;

import java.util.Objects;

//aggregate root-rich domain model
public class Product {

    private final ProductId id;
    private String productName;
    private Money price;
    private String description;
    private Integer stock;
    //TODO: minStockThreshold

    private BrandId brandId;
    private CategoryId categoryId;

    //TODO: for product lifecyle: productStatus(active, passive, out_of_stock, archived), publishedAt
    // for audit : createdAt, updatedAt,

    private Product(ProductId id, String productName, Money money,
                    String description, Integer stock, BrandId brandId, CategoryId categoryId) {
        this.id = id;
        this.productName = productName;
        this.price = money;
        this.description = description;
        this.stock = stock;
        this.brandId = brandId;
        this.categoryId = categoryId;
    }

    public static Product create(
            String productName, Money money,
            String description, Integer stock,
            BrandId brandId, CategoryId categoryId) {
        validateProductName(productName);
        validateDescription(description);
        validateStock(stock);
        return new Product(
                ProductId.generate(),
                productName,
                money,
                description,
                stock,
                brandId,
                categoryId
        );
    }

    public static Product rehydrate(
            ProductId id, String productName, Money money,
            String description, Integer stock, BrandId brandId, CategoryId categoryId) {
        return new Product(
                id,
                productName,
                money,
                description,
                stock,
                brandId,
                categoryId
        );
    }

    //validations
    public static void validateProductName(String productName){
        if (productName == null || productName.trim().isEmpty()){
            throw new InvalidProductNameException("name cannot be null");
        }
        if (productName.length() > 120){
            throw new InvalidProductNameException("Name length cannot be larger than 120 characters");
        }
    }

    public static void validateDescription(String description){
        if (description == null || description.trim().isEmpty()){
            throw new InvalidDescriptionException("Description cannot be null");
        }
        if (description.length() >= 255){
            throw new InvalidDescriptionException("Description length must be maximum 255 characters");
        }
    }

    public static void validateStock(Integer stock){
        if (stock == null || stock < 0){
            throw new InvalidStockException("Stock cannot be null or negative value");
        }
    }

    //worker methods
    public void changePrice(Money newPrice){
        Objects.requireNonNull(newPrice, "Price cannot be null");
        this.price = newPrice;
    }

    public void updateProductName(String productName){
        validateProductName(productName);
        this.productName = productName.trim();
    }

    public void updateDescription(String description){
        validateDescription(description);
        this.description = description.trim();
    }

    public void updateStock(Integer newStock){
        validateStock(newStock);
        this.stock = newStock;
    }

    //stok azalt
    public void dispatch(Integer quantityToDispatch){
        if (quantityToDispatch == null || quantityToDispatch < 0){
            throw new InvalidStockException("Quantity to dispatch must be positive");
        }
        if (this.stock < quantityToDispatch){
            throw new InsufficientStockException();
        }

        this.stock -= quantityToDispatch;
    }

    //stok arttır.
    public void restock(Integer quantityToRestock){
        if (quantityToRestock == null || quantityToRestock < 0){
            throw new InvalidStockException("Quantity to restock must be positive");
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

    public String description() {
        return description;
    }

    public Integer stock() {
        return stock;
    }

    public BrandId brandId() {
        return brandId;
    }

    public CategoryId categoryId() {
        return categoryId;
    }
}
