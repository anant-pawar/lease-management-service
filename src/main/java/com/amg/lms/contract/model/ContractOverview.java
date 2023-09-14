package com.amg.lms.contract.model;

import lombok.Data;

@Data
public class ContractOverview {
    private String id;
    private Integer contractNumber;
    private String customer;
    private String vehicle;
    private String vin;
    private Double monthlyRate;
    private Double vehiclePrice;
}
