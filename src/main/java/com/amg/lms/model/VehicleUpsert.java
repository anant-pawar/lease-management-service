package com.amg.lms.model;

import lombok.Data;

@Data
public class VehicleUpsert {
    private String brand;

    private String model;

    private Integer productionYear;

    private String vin;

    private Double price;

}
