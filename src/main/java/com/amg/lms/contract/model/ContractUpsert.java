package com.amg.lms.contract.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContractUpsert {
    @NotNull(message = "contract number is required")
    private Integer contractNumber;
    @NotNull(message = "monthly rate is required")
    private Double monthlyRate;
    @NotBlank(message = "customer id is required")
    private String customerId;
    @NotBlank(message = "vehicle id is required")
    private String vehicleId;

}
