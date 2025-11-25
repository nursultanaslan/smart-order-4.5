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
                entity.productName(),
                new Money(entity.amount(), entity.currency()),
                entity.brandName(),
                entity.description(),
                entity.stock()
        );
    }


    public JpaProductEntity toEntity(Product product){
        return new JpaProductEntity(
                product.id().value(),
                product.productName(),
                product.price().amount(),
                product.price().currency(),
                product.brandName(),
                product.description(),
                product.stock()
        );
    }
}
