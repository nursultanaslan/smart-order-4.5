package com.turkcell.customer_service.persistence.mapper;

import com.turkcell.customer_service.domain.model.*;
import com.turkcell.customer_service.persistence.entity.JpaCustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityMapper {

    public JpaCustomerEntity toEntity(Customer customer) {
        return new JpaCustomerEntity(
                customer.id().value(),
                customer.firstName(),
                customer.lastName(),
                customer.email().value(),
                customer.phoneNumber().value(),
                customer.address().country(),
                customer.address().city(),
                customer.address().street(),
                customer.address().postalCode(),
                customer.address().houseNumber());
    }

    public Customer toDomain(JpaCustomerEntity entity) {
        return Customer.rehydrate(
                new CustomerId(entity.id()),
                entity.firstName(),
                entity.lastName(),
                new Email(entity.email()),
                new Phone(entity.phoneNumber()),
                new Address(entity.country(),
                        entity.city(),
                        entity.street(),
                        entity.postalCode(),
                        entity.houseNumber()));
    }
}
