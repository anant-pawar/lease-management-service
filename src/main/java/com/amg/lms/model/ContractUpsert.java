package com.amg.lms.model;

import lombok.Data;

@Data
public class ContractUpsert {
    private Integer contractNumber;
    private Double monthlyRate;
    private String customerId;
    private String vehicleId;

}
