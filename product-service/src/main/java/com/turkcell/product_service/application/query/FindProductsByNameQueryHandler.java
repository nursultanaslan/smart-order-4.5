package com.turkcell.product_service.application.query;

import com.turkcell.product_service.application.dto.PageableProductResponse;
import com.turkcell.product_service.application.dto.ProductDto;
import com.turkcell.product_service.application.mapper.ProductMapper;
import com.turkcell.product_service.core.cqrs.QueryHandler;
import com.turkcell.product_service.domain.model.Product;
import com.turkcell.product_service.domain.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindProductsByNameQueryHandler implements QueryHandler<FindProductsByNameQuery, PageableProductResponse> {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public FindProductsByNameQueryHandler(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public PageableProductResponse handle(FindProductsByNameQuery query) {

        Page<Product> product = productRepository
                .findByProductNameIgnoreCase(query.productName(), PageRequest.of(query.pageIndex(), query.pageSize()));

        List<ProductDto> products = product
                .getContent()
                .stream()
                .map(productMapper::toDto)
                .toList();

        return new PageableProductResponse(products);
    }
}
