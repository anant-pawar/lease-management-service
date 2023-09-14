package com.amg.lms.vehicle.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleUpsert {
    @NotBlank(message = "brand cannot be blank")
    private String brand;

    @NotBlank(message = "model cannot be blank")
    private String model;

    @NotNull(message = "production year is required")
    private Integer productionYear;

    private String vin;

    @NotNull(message = "price is required")
    private Double price;

}
