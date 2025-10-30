package com.turkcell.product_service.infrastructure.persistence.repository;

import com.turkcell.product_service.domain.model.Product;
import com.turkcell.product_service.domain.model.ProductId;
import com.turkcell.product_service.domain.repository.ProductRepository;
import com.turkcell.product_service.infrastructure.persistence.mapper.ProductEntityMapper;
import com.turkcell.product_service.infrastructure.persistence.model.JpaProductEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryAdapter implements ProductRepository {
    private final JpaProductRepository productRepository;
    private final ProductEntityMapper productMapper;

    public ProductRepositoryAdapter(JpaProductRepository productRepository, ProductEntityMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Product save(Product product) {
        JpaProductEntity entity = productMapper.toEntity(product);
        entity = productRepository.save(entity);
        return productMapper.toDomain(entity);
    }

    @Override
    public Optional<Product> findById(ProductId productId) {
        return productRepository
                .findById(productId.value())
                .map(productMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return productRepository
                .findAll()
                .stream()
                .map(productMapper::toDomain)
                .toList();
    }

    @Override
    public List<Product> findAllPaged(Integer pageIndex, Integer pageSize) {
        return productRepository
                .findAll(PageRequest.of(pageIndex, pageSize))
                .stream()
                .map(productMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(ProductId productId) {
        productRepository.deleteById(productId.value());
    }

    @Override
    public void delete(Product product) {
        JpaProductEntity entity = productMapper.toEntity(product);
        productRepository.delete(entity);
    }
}
