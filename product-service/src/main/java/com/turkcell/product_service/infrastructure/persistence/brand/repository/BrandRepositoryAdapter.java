package com.turkcell.product_service.infrastructure.persistence.brand.repository;

import com.turkcell.product_service.domain.model.brand.Brand;
import com.turkcell.product_service.domain.model.brand.BrandId;
import com.turkcell.product_service.domain.repository.BrandRepository;
import com.turkcell.product_service.infrastructure.persistence.brand.mapper.JpaBrandMapper;
import com.turkcell.product_service.infrastructure.persistence.brand.model.JpaBrandEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BrandRepositoryAdapter implements BrandRepository {

    private final JpaBrandRepository brandRepository;
    private final JpaBrandMapper brandMapper;

    public BrandRepositoryAdapter(JpaBrandRepository brandRepository, JpaBrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public Brand save(Brand brand) {
        //domain modeli db işlemleri yapmaz bilmez
        JpaBrandEntity entity = brandMapper.toEntity(brand);
        entity = brandRepository.save(entity);
        return brandMapper.toDomain(entity);
    }

    @Override
    public Optional<Brand> findById(BrandId id) {
        return brandRepository
                .findById(id.value())
                .map(brandMapper::toDomain);
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository
                .findAll()
                .stream()
                .map(brandMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(BrandId id) {
        brandRepository.deleteById(id.value());
    }

    @Override
    public void delete(Brand brand) {
        JpaBrandEntity entity = brandMapper.toEntity(brand);
        brandRepository.delete(entity);
    }
}
