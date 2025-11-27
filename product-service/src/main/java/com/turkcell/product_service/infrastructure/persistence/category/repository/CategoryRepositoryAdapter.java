package com.turkcell.product_service.infrastructure.persistence.category.repository;

import com.turkcell.product_service.domain.model.category.Category;
import com.turkcell.product_service.domain.model.category.CategoryId;
import com.turkcell.product_service.domain.repository.CategoryRepository;
import com.turkcell.product_service.infrastructure.persistence.category.mapper.JpaCategoryMapper;
import com.turkcell.product_service.infrastructure.persistence.category.model.JpaCategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepositoryAdapter implements CategoryRepository {

    private final JpaCategoryRepository categoryRepository;
    private final JpaCategoryMapper categoryMapper;

    public CategoryRepositoryAdapter(JpaCategoryRepository categoryRepository, JpaCategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Category save(Category category) {
        JpaCategoryEntity entity =  categoryMapper.toEntity(category);
        entity = categoryRepository.save(entity);
        return categoryMapper.toDomain(entity);
    }

    @Override
    public Optional<Category> findById(CategoryId id) {
        return categoryRepository
                .findById(id.value())
                .map(categoryMapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(CategoryId id) {
        categoryRepository.deleteById(id.value());
    }

    @Override
    public void delete(Category category) {
        JpaCategoryEntity entity = categoryMapper.toEntity(category);
        categoryRepository.delete(entity);
    }
}
