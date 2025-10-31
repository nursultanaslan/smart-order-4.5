package com.turkcell.product_service.infrastructure.persistence.mapper;

import com.turkcell.product_service.domain.model.Money;
import com.turkcell.product_service.domain.model.Product;
import com.turkcell.product_service.domain.model.ProductId;
import com.turkcell.product_service.infrastructure.persistence.model.JpaProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityMapper {

    public Product toDomain(JpaProductEntity entity){
        return Product.rehydrate(
                new ProductId(entity.id()),
                entity.name(),
                new Money(entity.amount(), entity.currency()),
                entity.description(),
                entity.stock()
        );
    }


    public JpaProductEntity toEntity(Product product){
        JpaProductEntity entity = new JpaProductEntity();
        entity.setId(product.id().value());
        entity.setName(product.name());
        entity.setAmount(product.price().amount());
        entity.setCurrency(product.price().currency());
        entity.setDescription(product.description());
        entity.setStock(product.stock());
        return entity;
    }
}
