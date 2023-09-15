package com.amg.lms.contract.exception;

import jakarta.persistence.EntityNotFoundException;

public class ContractNotFoundException extends EntityNotFoundException {
    public ContractNotFoundException(String id, Exception cause) {
        super(String.format("contract with id %s not found", id), cause);
    }
}
