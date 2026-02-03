package com.turkcell.customer_service.application.mapper;

import com.turkcell.customer_service.application.dto.request.CreateCustomerRequest;
import com.turkcell.customer_service.application.dto.response.*;
import com.turkcell.customer_service.domain.model.Customer;
import com.turkcell.customer_service.domain.model.Email;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toDomain(CreateCustomerRequest request) {
        return Customer.create(
                request.username(),
                new Email(request.email())

        );
    }

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.id().value(),
                customer.firstName(),
                customer.lastName()
        );
    }

    public DeletedCustomerResponse toDeletedResponse(Customer customer) {
        return new DeletedCustomerResponse(
                customer.id().value());
    }

    public GetCustomerByIdResponse toGetByIdResponse(Customer customer) {
        return new GetCustomerByIdResponse(
                customer.firstName(),
                customer.lastName()
        );
    }

    public UpdatedAddressResponse toUpdatedResponse(Customer customer) {
        return new UpdatedAddressResponse(
                customer.address().country(),
                customer.address().city(),
                customer.address().street(),
                customer.address().postalCode(),
                customer.address().houseNumber()
        );
    }


    public UpdatedPersonalDetailsResponse toUpdatedPersonalDetailsResponse(Customer customer){
        return new UpdatedPersonalDetailsResponse(
                customer.firstName(),
                customer.lastName(),
                customer.email().value(),
                customer.phoneNumber().value()
        );
    }
}
