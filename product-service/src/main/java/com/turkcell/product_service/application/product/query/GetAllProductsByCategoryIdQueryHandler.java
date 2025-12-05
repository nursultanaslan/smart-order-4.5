package com.turkcell.product_service.application.product.query;

import com.turkcell.product_service.application.product.dto.PageableProductResponse;
import com.turkcell.product_service.application.product.dto.ProductDto;
import com.turkcell.product_service.application.product.mapper.ProductMapper;
import com.turkcell.product_service.core.cqrs.QueryHandler;
import com.turkcell.product_service.domain.model.category.CategoryId;
import com.turkcell.product_service.domain.model.product.Product;
import com.turkcell.product_service.domain.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllProductsByCategoryIdQueryHandler implements QueryHandler<GetAllProductsByCategoryIdQuery, PageableProductResponse> {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public GetAllProductsByCategoryIdQueryHandler(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public PageableProductResponse handle(GetAllProductsByCategoryIdQuery query) {
        Page<Product> product = productRepository
                .findByCategoryId(new CategoryId(query.categoryId()), PageRequest.of(query.pageIndex(), query.pageSize()));

        List<ProductDto> products = product
                .getContent()
                .stream().map(productMapper::toDto)
                .toList();

        return new PageableProductResponse(products);
    }
}
