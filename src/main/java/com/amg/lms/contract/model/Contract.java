package com.amg.lms.contract.model;


import com.amg.lms.customer.model.Customer;
import com.amg.lms.vehicle.model.Vehicle;
import lombok.Data;

@Data
public class Contract {
    private String id;
    private Integer contractNumber;
    private Double monthlyRate;
    private Vehicle vehicle;
    private Customer customer;
}
