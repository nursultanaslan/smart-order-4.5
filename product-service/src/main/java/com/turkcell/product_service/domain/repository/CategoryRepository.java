package com.turkcell.product_service.domain.repository;

import com.turkcell.product_service.domain.model.category.Category;
import com.turkcell.product_service.domain.model.category.CategoryId;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Category save(Category category);
    Optional<Category> findById(CategoryId id);
    List<Category> findAll();
    void deleteById(CategoryId id);
    void delete(Category category);
    boolean existsByCategoryNameIgnoreCase(String categoryName);
}
