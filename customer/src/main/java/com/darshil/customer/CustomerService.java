package com.darshil.customer;

import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository) {
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName()) // with Records instead of Classes, the getters for properties are not
                // called 'get[property]()' they are just '[property_name]()'
                .lastName(request.lastName())
                .email(request.email())
                .build();

        //todo: check if email valid
        //todo: check if email not taken
        //todo: store customer in db
        customerRepository.save(customer);
    }
}
