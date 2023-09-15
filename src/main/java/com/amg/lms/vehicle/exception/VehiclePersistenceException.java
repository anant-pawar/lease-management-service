package com.amg.lms.vehicle.exception;

import com.amg.lms.exception.PersistenceException;

public class VehiclePersistenceException extends PersistenceException {
    public VehiclePersistenceException(Exception cause) {
        super(String.format("failed to persist vehicle"), cause);
    }
}
