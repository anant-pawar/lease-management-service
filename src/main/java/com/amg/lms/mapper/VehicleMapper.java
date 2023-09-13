package com.amg.lms.mapper;

import com.amg.lms.model.Vehicle;
import com.amg.lms.model.VehicleUpsert;
import com.amg.lms.repository.entities.VehicleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    VehicleEntity map(VehicleUpsert vehicle);

    @Mapping(target = "id", source = "id")
    VehicleEntity map(VehicleUpsert vehicle, String id);

    Vehicle map(VehicleEntity vehicle);

    List<Vehicle> map(List<VehicleEntity> vehicles);
}
