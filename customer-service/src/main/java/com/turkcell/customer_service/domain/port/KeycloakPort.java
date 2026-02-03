package com.turkcell.customer_service.domain.port;

import com.turkcell.customer_service.domain.model.AuthToken;
import com.turkcell.customer_service.domain.model.Customer;
import com.turkcell.customer_service.domain.model.CustomerId;

import java.util.UUID;

public interface KeycloakPort {

    UUID createUser(String email, String username, String password);
    AuthToken authenticate(String email, String password);
    AuthToken refreshToken(String refreshToken);
    Customer getCustomerById(CustomerId id);
}
