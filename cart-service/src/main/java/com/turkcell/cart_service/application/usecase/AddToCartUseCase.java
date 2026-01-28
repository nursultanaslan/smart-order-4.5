package com.turkcell.cart_service.application.usecase;

import com.turkcell.cart_service.application.client.ProductClient;
import com.turkcell.cart_service.application.dto.response.ProductResponse;
import com.turkcell.cart_service.domain.model.Cart;
import com.turkcell.cart_service.domain.model.CartStatus;
import com.turkcell.cart_service.domain.model.CustomerId;
import com.turkcell.cart_service.domain.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Service
@Validated
public class AddToCartUseCase {

    private final CartRepository cartRepository;
    private final ProductClient productClient;

    public AddToCartUseCase(CartRepository cartRepository, ProductClient productClient) {
        this.cartRepository = cartRepository;
        this.productClient = productClient;
    }

    /**
     * ProductClient üzerinden güncel fiyat ve stogu al
     * CartRepository uzerinden mevcut sepeti getir yoksa yeni sepet yarat
     * domain methodu olan addItem methodu ile sepete ürün ekleme işlemini gerceklestir
     * save işlemi ile kaydet.
     * **/
    @Transactional
    public Cart addToCart(UUID customerId, UUID productId, Integer quantity ) {

        //ürünün stoğunu ve o anki güncel fiyatını alır.
        ProductResponse product = productClient.getProductById(productId);

        int quantityToAdd = quantity != null ? quantity : 1;

        //kullanıcıya ait sepet var ise getir yoksa yenisini create et.
        Cart cart = cartRepository.findByCustomerId(new CustomerId(customerId))
                .orElseGet(() ->
                        Cart.create(
                                new CustomerId(customerId),
                                BigDecimal.ZERO,
                                product.currency(),
                                OffsetDateTime.now(),
                                CartStatus.getDefault(),
                                new ArrayList<>()
                        ));

        cart.addItem(
                productId,
                quantityToAdd,
                product.unitPrice(),
                product.stock());

        return cartRepository.save(cart);

    }
}
