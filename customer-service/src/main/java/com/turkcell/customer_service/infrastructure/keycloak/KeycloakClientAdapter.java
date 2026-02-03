package com.turkcell.customer_service.infrastructure.keycloak;

import com.turkcell.customer_service.domain.model.AuthToken;
import com.turkcell.customer_service.domain.model.Customer;
import com.turkcell.customer_service.domain.model.CustomerId;
import com.turkcell.customer_service.domain.port.KeycloakPort;
import org.keycloak.admin.client.Keycloak;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Keycloak adapter implementing KeycloakPort.
 * Handles all Keycloak Admin API and Token Endpoint interactions.
 */

@Component
public class KeycloakClientAdapter implements KeycloakPort {

    private final Keycloak keycloakAdminClient;

    public KeycloakClientAdapter(Keycloak keycloakAdminClient) {
        this.keycloakAdminClient = keycloakAdminClient;
    }


    @Override
    public UUID createUser(String email, String username, String password) {
        return null;
    }

    @Override
    public AuthToken authenticate(String email, String password) {
        return null;
    }

    @Override
    public AuthToken refreshToken(String refreshToken) {
        return null;
    }

    @Override
    public Customer getCustomerById(CustomerId id) {
        return null;
    }
}
