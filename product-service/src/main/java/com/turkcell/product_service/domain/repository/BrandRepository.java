package com.turkcell.product_service.domain.repository;

import com.turkcell.product_service.domain.model.brand.Brand;
import com.turkcell.product_service.domain.model.brand.BrandId;

import java.util.List;
import java.util.Optional;

public interface BrandRepository {

    Brand save(Brand brand);
    Optional<Brand> findById(BrandId id);
    List<Brand> findAll();
    void deleteById(BrandId id);
    void delete(Brand brand);
    boolean existsByBrandNameIgnoreCase(String brandName);
}
