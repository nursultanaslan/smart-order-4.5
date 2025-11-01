package com.turkcell.product_service.adapter.web;

import com.turkcell.product_service.application.dto.request.CreateProductRequest;
import com.turkcell.product_service.application.dto.response.DeleteProductResponse;
import com.turkcell.product_service.application.dto.response.ProductResponse;
import com.turkcell.product_service.application.service.CreateProductUseCase;
import com.turkcell.product_service.application.service.DeleteProductUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

    private final CreateProductUseCase createProduct;
    private final DeleteProductUseCase deleteProduct;

    public ProductsController(CreateProductUseCase createProduct, DeleteProductUseCase deleteProduct) {
        this.createProduct = createProduct;
        this.deleteProduct = deleteProduct;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@RequestBody @Valid CreateProductRequest createProductRequest) {
        return createProduct.create(createProductRequest);
    }

    @DeleteMapping("/{id}")
    public DeleteProductResponse delete(@PathVariable UUID id){
        return deleteProduct.delete(id);
    }

}
