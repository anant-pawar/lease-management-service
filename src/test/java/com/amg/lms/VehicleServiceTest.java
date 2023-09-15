package com.amg.lms;

import com.amg.lms.vehicle.VehicleEntity;
import com.amg.lms.vehicle.VehicleMapper;
import com.amg.lms.vehicle.VehicleRepository;
import com.amg.lms.vehicle.VehicleService;
import com.amg.lms.vehicle.exception.VehicleNotFoundException;
import com.amg.lms.vehicle.exception.VehiclePersistenceException;
import com.amg.lms.vehicle.model.Vehicle;
import com.amg.lms.vehicle.model.VehicleUpsert;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VehicleServiceTest {

    private final VehicleMapper MAPPER = Mappers.getMapper(VehicleMapper.class);
    private VehicleRepository repository;
    private VehicleService service;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(VehicleRepository.class);
        service = new VehicleService(repository, MAPPER);
    }

    @Test
    @DisplayName("Create Vehicle Test")
    void testCreateVehicle() {
        // Arrange
        var vehicleUpsert = createVehicleUpsert();
        var vehicleEntity = MAPPER.map(vehicleUpsert);
        var createdVehicleEntity = createVehicleEntity(vehicleUpsert);
        var expectedVehicle = MAPPER.map(createdVehicleEntity);

        Mockito.when(repository.save(vehicleEntity))
                .thenReturn(createdVehicleEntity);

        // Act
        Vehicle vehicle = service.createVehicle(vehicleUpsert);

        // Assert
        assertEquals(expectedVehicle, vehicle, "Created vehicle should match the expected vehicle");
        verify(repository, times(1)).save(vehicleEntity);
    }

    @Test
    @DisplayName("Create Vehicle VehiclePersistenceException Test")
    void testCreateVehicle_VehiclePersistenceException() {
        // Arrange
        var vehicleUpsert = createVehicleUpsert();
        var vehicleEntity = MAPPER.map(vehicleUpsert);

        Mockito.when(repository.save(vehicleEntity))
                .thenThrow(RuntimeException.class);

        // Act
        Exception exception = assertThrows(VehiclePersistenceException.class, () -> service.createVehicle(vehicleUpsert));

        // Assert
        assertEquals("failed to persist vehicle", exception.getMessage());
    }

    @Test
    @DisplayName("Update Vehicle Test")
    void testUpdateVehicle() {
        // Arrange
        var id = UUID.randomUUID().toString();
        var vehicleUpsert = createVehicleUpsert();
        var vehicleEntity = MAPPER.map(vehicleUpsert, id);
        var updatedVehicleEntity = createVehicleEntity(vehicleUpsert);

        Mockito.when(repository.save(vehicleEntity))
                .thenReturn(updatedVehicleEntity);

        // Act
        service.updateVehicle(id, vehicleUpsert);

        // Assert
        verify(repository, times(1)).save(vehicleEntity);
    }

    @Test
    @DisplayName("Update Vehicle VehiclePersistenceException Test")
    void testUpdateVehicle_VehiclePersistenceException() {
        // Arrange
        var id = UUID.randomUUID().toString();
        var vehicleUpsert = createVehicleUpsert();
        var vehicleEntity = MAPPER.map(vehicleUpsert, id);

        Mockito.when(repository.save(vehicleEntity))
                .thenThrow(RuntimeException.class);

        // Act
        Exception exception = assertThrows(VehiclePersistenceException.class, () -> service.updateVehicle(id, vehicleUpsert));

        // Assert
        assertEquals("failed to persist vehicle", exception.getMessage());
    }

    @Test
    @DisplayName("Get Vehicle Test")
    void testGetVehicle() {
        // Arrange
        var id = UUID.randomUUID().toString();
        var vehicleEntity = createVehicleEntity(id);
        var expectedVehicle = MAPPER.map(vehicleEntity);

        Mockito.when(repository.getReferenceById(id))
                .thenReturn(vehicleEntity);

        // Act
        var vehicle = service.getVehicle(id);

        // Assert
        assertEquals(expectedVehicle, vehicle, "Retrieved vehicle should match the expected vehicle");
        verify(repository, times(1)).getReferenceById(id);
    }

    @Test
    @DisplayName("Get Vehicle VehicleNotFoundException Test")
    void testGetVehicle_VehicleNotFoundException() {
        // Arrange
        var id = UUID.randomUUID().toString();

        Mockito.when(repository.getReferenceById(id))
                .thenThrow(EntityNotFoundException.class);

        // Act
        Exception exception = assertThrows(VehicleNotFoundException.class, () -> service.getVehicle(id));

        // Assert
        assertEquals(String.format("vehicle with id %s not found", id), exception.getMessage());
    }

    @Test
    @DisplayName("Get Vehicles Test")
    void testGetVehicles() {
        // Arrange
        var page = 0;
        var size = 1;
        var pageRequest = PageRequest.of(page, size);
        var id = UUID.randomUUID().toString();
        var vehicleEntity = createVehicleEntity(id);
        var expectedVehicle = MAPPER.map(vehicleEntity);

        when(repository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(List.of(vehicleEntity)));

        // Act
        var vehicles = service.getVehicles(null, page, size);

        // Assert
        assertFalse(vehicles.getRecords().isEmpty(), "Returned vehicles list should not be empty");
        assertEquals(0, vehicles.getCurrentPage(), "Current page should be 0");
        assertEquals(1, vehicles.getTotalPages(), "Total pages should be 1");
        assertEquals(1, vehicles.getTotalItems(), "Total items should be 1");
        assertEquals(expectedVehicle, vehicles.getRecords().get(0), "Retrieved vehicle should match the expected vehicle");
        verify(repository, times(1)).findAll(pageRequest);
    }

    private VehicleUpsert createVehicleUpsert() {
        var vehicleUpsert = new VehicleUpsert();
        vehicleUpsert.setVin("VIN_FAKE");
        vehicleUpsert.setModel("MODEL_FAKE");
        vehicleUpsert.setBrand("BRAND_FAKE");
        vehicleUpsert.setPrice(10000.0);
        vehicleUpsert.setProductionYear(2000);
        return vehicleUpsert;
    }

    private VehicleEntity createVehicleEntity(final VehicleUpsert vehicleUpsert) {
        var vehicle = MAPPER.map(vehicleUpsert);
        vehicle.setId(UUID.randomUUID().toString());
        return vehicle;
    }

    private VehicleEntity createVehicleEntity(String id) {
        var vehicle = new VehicleEntity();
        vehicle.setId(id);
        vehicle.setVin("VIN_FAKE");
        vehicle.setModel("MODEL_FAKE");
        vehicle.setBrand("BRAND_FAKE");
        vehicle.setPrice(10000.0);
        vehicle.setProductionYear(2000);
        return vehicle;
    }
}