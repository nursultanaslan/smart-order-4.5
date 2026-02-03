package com.turkcell.customer_service.infrastructure.persistence.repository;

import com.turkcell.customer_service.domain.model.Customer;
import com.turkcell.customer_service.domain.model.CustomerId;
import com.turkcell.customer_service.domain.port.CustomerRepository;
import com.turkcell.customer_service.infrastructure.persistence.entity.JpaCustomerEntity;
import com.turkcell.customer_service.infrastructure.persistence.mapper.CustomerEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerEntityMapper customerEntityMapper;
    private final JpaCustomerRepository customerRepository;

    public CustomerRepositoryAdapter(CustomerEntityMapper customerEntityMapper, JpaCustomerRepository customerRepository) {
        this.customerEntityMapper = customerEntityMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        JpaCustomerEntity entity = customerEntityMapper.toEntity(customer);
        entity = customerRepository.save(entity);
        return customerEntityMapper.toDomain(entity);
    }

    @Override
    public Optional<Customer> findById(CustomerId customerId) {
        return customerRepository.findById(customerId.value())
                .map(customerEntityMapper::toDomain);
    }

    @Override
    public List<Customer> findAllPaged(Integer pageIndex, Integer pageSize) {
        return List.of();
    }

    @Override
    public void deleteById(CustomerId id) {
        customerRepository.deleteById(id.value());
    }

    @Override
    public void delete(Customer customer) {
        JpaCustomerEntity entity = customerEntityMapper.toEntity(customer);
        customerRepository.delete(entity);
    }
}
