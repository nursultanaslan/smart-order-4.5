package com.turkcell.product_service.web;

import com.turkcell.product_service.application.command.CreateProductCommand;
import com.turkcell.product_service.application.dto.ProductResponse;
import com.turkcell.product_service.core.cqrs.CommandHandler;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

    private final CommandHandler<CreateProductCommand, ProductResponse>  createProductCommandHandler;

    public ProductsController(CommandHandler<CreateProductCommand, ProductResponse> createProductCommandHandler) {
        this.createProductCommandHandler = createProductCommandHandler;
    }

    @PostMapping
    public ProductResponse createProduct(@RequestBody CreateProductCommand command) {
        return createProductCommandHandler.handle(command);
    }


}
