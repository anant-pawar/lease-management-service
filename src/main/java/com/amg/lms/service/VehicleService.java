package com.amg.lms.service;

import com.amg.lms.mapper.VehicleMapper;
import com.amg.lms.model.RecordsPage;
import com.amg.lms.model.Vehicle;
import com.amg.lms.model.VehicleUpsert;
import com.amg.lms.repository.VehicleRepository;
import com.amg.lms.repository.entities.VehicleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository repository;
    private final VehicleMapper mapper;

    public Vehicle createVehicle(final VehicleUpsert vehicle) {
        final VehicleEntity vehicleEntity = mapper.map(vehicle);
        final VehicleEntity updatedVehicleEntity = repository.save(vehicleEntity);
        return mapper.map(updatedVehicleEntity);
    }

    public Vehicle updateVehicle(final String id, final VehicleUpsert vehicle) {
        final VehicleEntity vehicleEntity = mapper.map(vehicle, id);
        final VehicleEntity updatedVehicleEntity = repository.save(vehicleEntity);
        return mapper.map(updatedVehicleEntity);
    }

    public Vehicle getVehicle(final String id) {
        final VehicleEntity vehicle = repository.getReferenceById(id);
        return mapper.map(vehicle);
    }

    public RecordsPage<Vehicle> getVehicles(final String name, final int page, final int size) {
        final Pageable paging = PageRequest.of(page, size);
        final Page<VehicleEntity> pageVehicles = (name == null) ? repository.findAll(paging) : repository.findByVinContaining(name, paging);
        final List<VehicleEntity> vehicleEntities = pageVehicles.getContent();
        final List<Vehicle> vehicles = mapper.map(vehicleEntities);

        return RecordsPage.<Vehicle>builder()
                .records(vehicles)
                .totalPages(pageVehicles.getTotalPages())
                .currentPage(pageVehicles.getNumber())
                .totalItems(pageVehicles.getTotalElements())
                .build();
    }
}
