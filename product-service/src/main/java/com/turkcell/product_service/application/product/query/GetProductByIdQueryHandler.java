package com.turkcell.product_service.application.product.query;

import com.turkcell.product_service.application.product.dto.GetProductByIdResponse;
import com.turkcell.product_service.application.product.dto.ProductResponse;
import com.turkcell.product_service.application.product.exception.ProductNotFoundException;
import com.turkcell.product_service.application.product.mapper.ProductMapper;
import com.turkcell.product_service.core.cqrs.QueryHandler;
import com.turkcell.product_service.domain.model.product.Product;
import com.turkcell.product_service.domain.model.product.ProductId;
import com.turkcell.product_service.domain.repository.ProductRepository;
import org.springframework.stereotype.Component;


@Component
public class GetProductByIdQueryHandler implements QueryHandler<GetProductByIdQuery, GetProductByIdResponse> {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public GetProductByIdQueryHandler(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public GetProductByIdResponse handle(GetProductByIdQuery query) {

        Product product = productRepository.findById(new ProductId(query.productId()))
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return productMapper.toGetByIdResponse(product);

    }
}
