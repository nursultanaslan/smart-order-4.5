package com.turkcell.product_service.domain.repository;

import com.turkcell.product_service.domain.model.Product;
import com.turkcell.product_service.domain.model.ProductId;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);
    Optional<Product> findById(ProductId productId);
    List<Product> findAll();
    List<Product> findAllPaged(Integer pageIndex, Integer pageSize);
    void deleteById(ProductId productId);
    void delete(Product product);
}
