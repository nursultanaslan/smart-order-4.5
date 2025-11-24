package com.turkcell.customer_service.domain.model;

//rich domain
//Customer entitysinin gercek hayattaki davranısları nelerdir?
public class Customer {

    private final CustomerId id;

    private String firstName;
    private String lastName;

    private Email email;
    private Phone phoneNumber;
    private Address address;

    private Customer(CustomerId id,  String firstName, String lastName, Email email, Phone phoneNumber, Address address) {
        validateInputs(firstName,lastName);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public static Customer create(String firstName, String lastName, Email email, Phone phoneNumber, Address address) {
        return new Customer(
                CustomerId.generate(),
                firstName,
                lastName,
                email,
                phoneNumber,
                address
        );
    }

    public static Customer rehydrate(CustomerId id, String firstName, String lastName, Email email, Phone phoneNumber, Address address) {

        return new Customer(
                id,
                firstName,
                lastName,
                email,
                phoneNumber,
                address
        );
    }

    //behaviors
    public void updateFirstName(String newFirstName) {
        this.firstName = newFirstName;
    }

    public void updateLastName(String newLastName) {
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
    public static void validateInputs(String firstName, String lastName){
        if (firstName == null || firstName.isEmpty()){
            throw new IllegalArgumentException("First name is null or empty");
        }
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

    public Email email() {
        return email;
    }

    public Phone phoneNumber() {
        return phoneNumber;
    }

    public Address address() {
        return address;
    }
}
