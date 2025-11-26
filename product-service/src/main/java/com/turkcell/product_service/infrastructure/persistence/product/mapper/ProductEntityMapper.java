package com.turkcell.product_service.infrastructure.persistence.product.mapper;

import com.turkcell.product_service.domain.model.brand.BrandId;
import com.turkcell.product_service.domain.model.category.CategoryId;
import com.turkcell.product_service.domain.model.product.Money;
import com.turkcell.product_service.domain.model.product.Product;
import com.turkcell.product_service.domain.model.product.ProductId;
import com.turkcell.product_service.infrastructure.persistence.product.model.JpaProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityMapper {

    public Product toDomain(JpaProductEntity entity){
        return Product.rehydrate(
                new ProductId(entity.id()),
                entity.productName(),
                new Money(entity.amount(), entity.currency()),
                entity.description(),
                entity.stock(),
                new BrandId(entity.brandId()),
                new CategoryId(entity.categoryId())
        );
    }


    public JpaProductEntity toEntity(Product product){
        return new JpaProductEntity(
                product.id().value(),
                product.productName(),
                product.price().amount(),
                product.price().currency(),
                product.description(),
                product.stock(),
                product.brandId().value(),
                product.categoryId().value()
        );
    }
}
