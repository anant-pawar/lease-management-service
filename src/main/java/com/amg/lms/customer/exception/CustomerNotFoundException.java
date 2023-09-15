package com.amg.lms.customer.exception;

import jakarta.persistence.EntityNotFoundException;

public class CustomerNotFoundException extends EntityNotFoundException {
    public CustomerNotFoundException(String id, Exception cause) {
        super(String.format("customer with id %s not found", id), cause);
    }
}
