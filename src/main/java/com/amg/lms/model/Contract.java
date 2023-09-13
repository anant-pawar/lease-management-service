package com.amg.lms.model;


import lombok.Data;

@Data
public class Contract {
    private String id;
    private Integer contractNumber;
    private Double monthlyRate;
    private Vehicle vehicle;
    private Customer customer;
}
