package com.turkcell.customer_service.domain.port;

import com.turkcell.customer_service.domain.model.Customer;
import com.turkcell.customer_service.domain.model.CustomerId;

import java.util.List;
import java.util.Optional;


public interface CustomerRepository {

    Customer save(Customer customer);
    Optional<Customer> findById(CustomerId id);
    List<Customer> findAllPaged(Integer pageIndex, Integer pageSize);
    void deleteById(CustomerId id);
    void delete(Customer customer);

}
