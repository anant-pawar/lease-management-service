package com.amg.lms.vehicle;

import com.amg.lms.vehicle.model.Vehicle;
import com.amg.lms.vehicle.model.VehicleUpsert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(target = "id", ignore = true)
    VehicleEntity map(VehicleUpsert vehicle);

    @Mapping(target = "id", source = "id")
    VehicleEntity map(VehicleUpsert vehicle, String id);

    Vehicle map(VehicleEntity vehicle);

    List<Vehicle> map(List<VehicleEntity> vehicles);
}
