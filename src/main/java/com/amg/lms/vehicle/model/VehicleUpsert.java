package com.amg.lms.vehicle.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VehicleUpsert {
    @NotBlank(message = "brand cannot be blank")
    private String brand;

    @NotBlank(message = "model cannot be blank")
    private String model;

    private Integer productionYear;

    private String vin;

    private Double price;

}
