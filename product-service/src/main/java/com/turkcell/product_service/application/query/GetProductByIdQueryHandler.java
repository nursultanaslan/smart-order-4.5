package com.turkcell.product_service.application.query;

import com.turkcell.product_service.application.dto.ProductResponse;
import com.turkcell.product_service.application.exception.ProductNotFoundException;
import com.turkcell.product_service.application.mapper.ProductMapper;
import com.turkcell.product_service.core.cqrs.QueryHandler;
import com.turkcell.product_service.domain.model.Product;
import com.turkcell.product_service.domain.model.ProductId;
import com.turkcell.product_service.domain.repository.ProductRepository;
import org.springframework.stereotype.Component;


@Component
public class GetProductByIdQueryHandler implements QueryHandler<GetProductByIdQuery, ProductResponse> {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public GetProductByIdQueryHandler(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponse handle(GetProductByIdQuery query) {

        Product product = productRepository.findById(new ProductId(query.productId()))
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return productMapper.toResponse(product);

    }
}
