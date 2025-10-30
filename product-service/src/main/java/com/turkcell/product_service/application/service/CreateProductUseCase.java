package com.turkcell.product_service.application.service;

import com.turkcell.product_service.application.dto.request.CreateProductRequest;
import com.turkcell.product_service.application.dto.response.ProductResponse;
import com.turkcell.product_service.application.mapper.ProductMapper;
import com.turkcell.product_service.domain.model.Product;
import com.turkcell.product_service.domain.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class CreateProductUseCase {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public CreateProductUseCase(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductResponse create(@Valid CreateProductRequest request) {
        Product product = productMapper.toDomain(request);
        product = productRepository.save(product);
        return productMapper.toResponse(product);
    }
}
