package com.turkcell.customer_service.domain.model;

/**
 * Customer roles matching Keycloak realm roles.
 * Must match role names configured in Keycloak (realm: example).
 */
public enum Role {
    CUSTOMER,   // Standard user role
    ADMIN;       // System administrator

    public static Role fromString(String roleStr) {
        if (roleStr == null) {
            return null;
        }

        //Handle lowercase from Keycloak
        String normalizedRole = roleStr.toUpperCase().replace("-", "_");
        try {
            return Role.valueOf(normalizedRole);
        }catch (IllegalArgumentException e) {
            // Unknown role - ignore or log warning
            return null;
        }
    }

    /**
     * Convert to Keycloak role string
     */
    public String toKeycloakRole() {
        return this.name().toLowerCase().replace("_", "-");
    }
}
