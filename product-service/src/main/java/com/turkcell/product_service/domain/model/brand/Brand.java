package com.turkcell.product_service.domain.model.brand;

import com.turkcell.product_service.domain.exception.InvalidBrandNameException;

//aggregate root -> has own lifecycle
public class Brand {

    private final BrandId id;
    private String brandName;

    private Brand(BrandId id, String brandName) {
        this.id = id;
        this.brandName = brandName;
    }

    public static Brand create(String brandName) {
        validateBrandName(brandName);
        return new Brand(
                BrandId.generate(),
                brandName
        );
    }

    public static Brand rehydrate(BrandId id, String brandName) {
        return new Brand(
                id,
                brandName
        );
    }

    //TODO: delete brand anında o markaya ait productlar bulunuyorsa o marka silinememeli.
    public static void validateBrandName(String brandName) {
        if (brandName == null || brandName.isBlank()) {
            throw new InvalidBrandNameException("BrandName cannot be null or blank");
        }
    }

    public void updateBrandName(String newBrandName) {
        validateBrandName(newBrandName);
        this.brandName = newBrandName.trim();
    }

    public BrandId id() {
        return id;
    }

    public String brandName() {
        return brandName;
    }
}
