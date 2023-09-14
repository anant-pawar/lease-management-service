package com.amg.lms.vehicle.model;

import lombok.Data;

@Data
public class Vehicle {

    private String id;

    private String brand;

    private String model;

    private Integer productionYear;

    private String vin;

    private Double price;

}
