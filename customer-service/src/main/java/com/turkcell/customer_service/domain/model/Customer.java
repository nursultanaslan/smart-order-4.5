package com.turkcell.customer_service.domain.model;

import java.time.OffsetDateTime;
import java.util.List;

//rich domain
//Customer entitysinin gercek hayattaki davranısları nelerdir?
//password domainin işi değil keycloakta tutulur.
public class Customer {

    private final CustomerId id;

    private String firstName;
    private String lastName;
    private String username;

    private List<Role> roles;
    private Email email;
    private Phone phoneNumber;
    private Address address;

    private boolean emailVerified;
    private OffsetDateTime createdAt;

    private Customer(CustomerId id, String firstName, String lastName, String username, List<Role> roles, Email email, Phone phoneNumber, Address address, boolean emailVerified, OffsetDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.roles = roles;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.emailVerified = emailVerified;
        this.createdAt = createdAt;
    }

    public static Customer create(String firstName, String lastName, String username, List<Role> roles, Email email, Phone phoneNumber, OffsetDateTime createdAt) {
        return new Customer(
                CustomerId.generate(),
                firstName,
                lastName,
                username,
                roles,
                email,
                phoneNumber,
                null,
                false,   //ilk creation zamanında false.
                OffsetDateTime.now()
                );
    }

    public static Customer rehydrate(CustomerId id, String firstName, String lastName,String username, List<Role> roles, Email email, Phone phoneNumber, Address address, Boolean emailVerified, OffsetDateTime createdAt) {
        return new Customer(
                id,
                firstName,
                lastName,
                username,
                roles,
                email,
                phoneNumber,
                address,
                emailVerified,
                createdAt
        );
    }

    //behaviors
    public void updateFirstName(String newFirstName) {
        validateFirstName(newFirstName);
        this.firstName = newFirstName;
    }

    public void updateLastName(String newLastName) {
        validateLastName(newLastName);
        this.lastName = newLastName;
    }

    public void updateEmail(Email newEmail) {
        this.email = newEmail;
    }

    public void updatePhone(Phone newPhone) {
        this.phoneNumber = newPhone;
    }
    public void updateAddress(Address newAddress) {
        this.address = newAddress;
    }

    public void addNewAddress(Address newAddress) {
        this.address = newAddress;
    }


    //validate methods
    public static void validateFirstName(String firstName){
        if (firstName == null || firstName.isEmpty()){
            throw new IllegalArgumentException("First name is null or empty");
        }
    }

    public static void validateLastName(String lastName){
        if (lastName == null || lastName.isEmpty()){
            throw new IllegalArgumentException("Last name is null or empty");
        }
    }


    //getters
    public CustomerId id() {
        return id;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String username() {
        return username;
    }

    public List<Role> roles() {
        return roles;
    }

    public Email email() {
        return email;
    }

    public Phone phoneNumber() {
        return phoneNumber;
    }

    public Address address() {
        return address;
    }

    public boolean emailVerified() {
        return emailVerified;
    }

    public OffsetDateTime createdAt() {
        return createdAt;
    }
}
