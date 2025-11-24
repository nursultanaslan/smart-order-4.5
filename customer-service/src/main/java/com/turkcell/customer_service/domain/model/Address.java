package com.turkcell.customer_service.domain.model;

import java.util.Objects;

public record Address(String country, String city, String street, String postalCode, Integer houseNumber) {

    public Address{
        Objects.requireNonNull(country, "Country cannot be null");
        Objects.requireNonNull(city, "City cannot be null");
        Objects.requireNonNull(street, "Street cannot be null");
        Objects.requireNonNull(postalCode, "Postal Code cannot be null");
        Objects.requireNonNull(houseNumber, "House Number cannot be null");

        if (country.isBlank()){
            throw new IllegalArgumentException("Country cannot be blank");
        }
        if (city.isBlank()){
            throw new IllegalArgumentException("City cannot be blank");
        }
        if (street.isBlank()){
            throw new IllegalArgumentException("Street cannot be blank");
        }
        if (postalCode.isBlank()){
            throw new IllegalArgumentException("Postal Code cannot be blank");
        }
        if (postalCode.length()<6){
            throw new IllegalArgumentException("Postal Code length should be min 5 chars");
        }
        if (houseNumber <= 0){
            throw new IllegalArgumentException("House Number cannot be negative");
        }
    }
}
