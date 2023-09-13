package com.amg.lms.service;

import com.amg.lms.mapper.VehicleMapper;
import com.amg.lms.model.RecordsPage;
import com.amg.lms.model.Vehicle;
import com.amg.lms.model.VehicleUpsert;
import com.amg.lms.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Service class for managing vehicles.
 */
@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository repository;
    private final VehicleMapper mapper;

    /**
     * Create a new vehicle.
     *
     * @param vehicle The vehicle to create.
     * @return The created vehicle.
     */
    public Vehicle createVehicle(final VehicleUpsert vehicle) {
        final var vehicleEntity = mapper.map(vehicle);
        final var updatedVehicleEntity = repository.save(vehicleEntity);
        return mapper.map(updatedVehicleEntity);
    }

    /**
     * Update an existing vehicle.
     *
     * @param id      The ID of the vehicle to update.
     * @param vehicle The updated vehicle data.
     */
    public void updateVehicle(final String id, final VehicleUpsert vehicle) {
        final var vehicleEntity = mapper.map(vehicle, id);
        repository.save(vehicleEntity);
    }

    /**
     * Retrieve a vehicle by its ID.
     *
     * @param id The ID of the vehicle to retrieve.
     * @return The retrieved vehicle.
     */
    public Vehicle getVehicle(final String id) {
        final var vehicle = repository.getReferenceById(id);
        return mapper.map(vehicle);
    }

    /**
     * Get a paginated list of vehicles based on a name filter.
     *
     * @param name The name filter.
     * @param page The page number.
     * @param size The number of vehicles per page.
     * @return A page of vehicles.
     */
    public RecordsPage<Vehicle> getVehicles(final String name, final int page, final int size) {
        final var paging = PageRequest.of(page, size);
        final var pageVehicles = (name == null) ? repository.findAll(paging) : repository.findByVinContaining(name, paging);
        final var vehicles = mapper.map(pageVehicles.getContent());

        return RecordsPage.<Vehicle>builder()
                .records(vehicles)
                .totalPages(pageVehicles.getTotalPages())
                .currentPage(pageVehicles.getNumber())
                .totalItems(pageVehicles.getTotalElements())
                .build();
    }
}
