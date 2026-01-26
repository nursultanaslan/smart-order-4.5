package com.turkcell.cart_service.web.controller;

import com.turkcell.cart_service.application.usecase.AddToCartUseCase;
import com.turkcell.cart_service.domain.model.Cart;
import com.turkcell.cart_service.web.dto.request.CartItemRequest;
import com.turkcell.cart_service.web.dto.response.CartItemResponse;
import com.turkcell.cart_service.web.mapper.CartMapper;
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

    private final AddToCartUseCase addToCartUseCase;
    private final CartMapper cartMapper;

    public CartsController(AddToCartUseCase addToCartUseCase, CartMapper cartMapper) {
        this.addToCartUseCase = addToCartUseCase;
        this.cartMapper = cartMapper;
    }

    @PostMapping("/{customerId}")
    public CartItemResponse addToCart(@PathVariable UUID customerId, @Valid @RequestBody CartItemRequest request) {

        Cart cart = addToCartUseCase.addToCart(
                customerId,
                request.productId(),
                request.quantity()
        );

        return cartMapper.toCartItemResponse(cart, request.productId());
    }

}
