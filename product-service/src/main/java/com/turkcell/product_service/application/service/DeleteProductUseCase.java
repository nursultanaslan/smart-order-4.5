package com.turkcell.product_service.application.service;

import com.turkcell.product_service.application.dto.response.DeleteProductResponse;
import com.turkcell.product_service.application.mapper.DeleteProductMapper;
import com.turkcell.product_service.domain.model.Product;
import com.turkcell.product_service.domain.model.ProductId;
import com.turkcell.product_service.domain.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class DeleteProductUseCase {

    private final ProductRepository productRepository;
    private final DeleteProductMapper productMapper;

    public DeleteProductUseCase(ProductRepository productRepository, DeleteProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public DeleteProductResponse delete(UUID id) {
        Product product = productRepository.findById(new ProductId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        productRepository.delete(product);
        return productMapper.toResponse(product);
    }
}
