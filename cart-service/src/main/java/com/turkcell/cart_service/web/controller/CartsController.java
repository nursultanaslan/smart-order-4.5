package com.turkcell.cart_service.web.controller;

import com.turkcell.cart_service.web.dto.request.CartItemRequest;
import com.turkcell.cart_service.web.dto.response.CartItemResponse;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/openapi/v1/carts")
@Validated
public class CartsController{

    @PostMapping("/{customerId}")
    public CartItemResponse addToCart(@PathVariable UUID customerId, @Valid @RequestBody CartItemRequest request) {
        return new CartItemResponse(request.productId(), request.quantity());
    }

}
