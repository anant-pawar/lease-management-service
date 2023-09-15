package com.amg.lms.customer.exception;

import com.amg.lms.exception.PersistenceException;

public class CustomerPersistenceException extends PersistenceException {
    public CustomerPersistenceException(Exception cause) {
        super(String.format("failed to persist customer"), cause);
    }
}
