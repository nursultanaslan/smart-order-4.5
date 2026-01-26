package com.turkcell.product_service.domain.event;

import com.turkcell.product_service.domain.model.product.ProductId;

//Cart service bu eventi dinleyecek.
//sepetinde silinen ürün olan kullanıcıların sepetinden ürünü otomatik çıkarır veya "Tükendi" uyarısı verir.
public record ProductDeletedEvent(ProductId id, Integer stock) {
}
