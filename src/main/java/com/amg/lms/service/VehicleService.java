package com.amg.lms.service;

import com.amg.lms.mapper.VehicleMapper;
import com.amg.lms.model.RecordsPage;
import com.amg.lms.model.Vehicle;
import com.amg.lms.model.VehicleUpsert;
import com.amg.lms.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository repository;
    private final VehicleMapper mapper;

    public Vehicle createVehicle(final VehicleUpsert vehicle) {
        final var vehicleEntity = mapper.map(vehicle);
        final var updatedVehicleEntity = repository.save(vehicleEntity);
        return mapper.map(updatedVehicleEntity);
    }

    public Vehicle updateVehicle(final String id, final VehicleUpsert vehicle) {
        final var vehicleEntity = mapper.map(vehicle, id);
        final var updatedVehicleEntity = repository.save(vehicleEntity);
        return mapper.map(updatedVehicleEntity);
    }

    public Vehicle getVehicle(final String id) {
        final var vehicle = repository.getReferenceById(id);
        return mapper.map(vehicle);
    }

    public RecordsPage<Vehicle> getVehicles(final String name, final int page, final int size) {
        final var paging = PageRequest.of(page, size);
        final var pageVehicles = (name == null) ? repository.findAll(paging) : repository.findByVinContaining(name, paging);
        final var vehicleEntities = pageVehicles.getContent();
        final var vehicles = mapper.map(vehicleEntities);

        return RecordsPage.<Vehicle>builder()
                .records(vehicles)
                .totalPages(pageVehicles.getTotalPages())
                .currentPage(pageVehicles.getNumber())
                .totalItems(pageVehicles.getTotalElements())
                .build();
    }
}
