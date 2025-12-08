package com.turkcell.product_service.application.product.command;

import com.turkcell.product_service.application.product.dto.ProductResponse;
import com.turkcell.product_service.application.product.exception.ProductNotFoundException;
import com.turkcell.product_service.application.product.mapper.ProductMapper;
import com.turkcell.product_service.core.cqrs.CommandHandler;
import com.turkcell.product_service.domain.model.product.Product;
import com.turkcell.product_service.domain.model.product.ProductId;
import com.turkcell.product_service.domain.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
public class IncreaseProductStockCommandHandler implements CommandHandler<IncreaseProductStockCommand, ProductResponse> {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public IncreaseProductStockCommandHandler(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponse handle(IncreaseProductStockCommand command) {
        Product product = productRepository.findById(new ProductId(command.productId()))
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));

        product.restock(command.quantity());

        Product updatedProduct = productRepository.save(product);

        return productMapper.toResponse(updatedProduct);

    }
}
