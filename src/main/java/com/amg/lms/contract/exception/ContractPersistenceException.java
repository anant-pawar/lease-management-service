package com.amg.lms.contract.exception;

import com.amg.lms.exception.PersistenceException;

public class ContractPersistenceException extends PersistenceException {
    public ContractPersistenceException(Exception cause) {
        super(String.format("failed to persist contract"), cause);
    }
}
