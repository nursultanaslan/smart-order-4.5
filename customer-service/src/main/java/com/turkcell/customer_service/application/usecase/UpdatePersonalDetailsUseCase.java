package com.turkcell.customer_service.application.usecase;

import com.turkcell.customer_service.application.dto.request.UpdatePersonalDetailsRequest;
import com.turkcell.customer_service.application.dto.response.UpdatedPersonalDetailsResponse;
import com.turkcell.customer_service.domain.exception.CustomerNotFoundException;
import com.turkcell.customer_service.application.mapper.CustomerMapper;
import com.turkcell.customer_service.domain.model.Customer;
import com.turkcell.customer_service.domain.model.CustomerId;
import com.turkcell.customer_service.domain.model.Email;
import com.turkcell.customer_service.domain.model.Phone;
import com.turkcell.customer_service.domain.port.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdatePersonalDetailsUseCase {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public UpdatePersonalDetailsUseCase(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public UpdatedPersonalDetailsResponse updateCustomer(UpdatePersonalDetailsRequest request) {
        CustomerId id = new CustomerId(request.customerId());
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + id.value()));

        customer.updateFirstName(request.firstName());
        customer.updateLastName(request.lastName());
        customer.updateEmail(new Email(request.email()));
        customer.updatePhone(new Phone(request.phone()));

        Customer updatedCustomer = customerRepository.save(customer);

        return customerMapper.toUpdatedPersonalDetailsResponse(updatedCustomer);
    }
}
