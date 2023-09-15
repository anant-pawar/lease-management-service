package com.amg.lms.vehicle.exception;

import jakarta.persistence.EntityNotFoundException;

public class VehicleNotFoundException extends EntityNotFoundException {
    public VehicleNotFoundException(String id, Exception cause) {
        super(String.format("vehicle with id %s not found", id), cause);
    }
}
