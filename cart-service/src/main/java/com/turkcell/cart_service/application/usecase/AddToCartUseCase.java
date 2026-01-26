package com.turkcell.cart_service.application.usecase;

import com.turkcell.cart_service.application.client.ProductClient;
import com.turkcell.cart_service.web.dto.response.CartItemResponse;
import com.turkcell.cart_service.application.dto.response.ProductResponse;
import com.turkcell.cart_service.domain.model.Cart;
import com.turkcell.cart_service.domain.model.CustomerId;
import com.turkcell.cart_service.domain.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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
    public Cart addToCart(UUID customerId, UUID productId, Integer quantity ) {
        //ürünün stoğunu ve o anki güncel fiyatını alır.
        ProductResponse product = productClient.getProductById(productId);

        //kullanıcıya ait sepet var ise getir yoksa yenisini create et.
        Cart cart = cartRepository.findByCustomerId(new CustomerId(customerId))
                .orElse(null);
        if (cart == null) {
            Cart.create(
                    customerId,

            )
        }

        cart.addItemToCart(item);

        cartRepository.save(cart);
        return new CartItemResponse(
                productId,
                quantity,
                product.quantity()
        );
    }
}
