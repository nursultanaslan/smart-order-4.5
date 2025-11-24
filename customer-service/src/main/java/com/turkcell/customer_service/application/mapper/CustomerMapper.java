package com.turkcell.customer_service.application.mapper;

import com.turkcell.customer_service.application.dto.request.CreateCustomerRequest;
import com.turkcell.customer_service.application.dto.response.CustomerResponse;
import com.turkcell.customer_service.domain.model.Address;
import com.turkcell.customer_service.domain.model.Customer;
import com.turkcell.customer_service.domain.model.Email;
import com.turkcell.customer_service.domain.model.Phone;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toDomain(CreateCustomerRequest request) {
        return Customer.create(
                request.firstName(),
                request.lastName(),
                new Email(request.email()),
                new Phone(request.phoneNumber()),
                new Address(request.country(),
                        request.city(),
                        request.street(),
                        request.postalCode(),
                        request.houseNumber()));
    }

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.id().value(),
                customer.firstName(),
                customer.lastName()
        );
    }
}
