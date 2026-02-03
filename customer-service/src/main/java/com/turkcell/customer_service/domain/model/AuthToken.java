package com.turkcell.customer_service.domain.model;

/**
 * Authentication token containing access token and refresh token.
 * Returned after successful login or token refresh.
 */
public class AuthToken {

    private String accessToken;
    private String refreshToken;
    private Integer expiresIn;
    private String tokenType;
    private String userId;
}
