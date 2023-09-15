package com.amg.lms.vehicle;

import com.amg.lms.model.RecordsPage;
import com.amg.lms.vehicle.exception.VehicleNotFoundException;
import com.amg.lms.vehicle.exception.VehiclePersistenceException;
import com.amg.lms.vehicle.model.Vehicle;
import com.amg.lms.vehicle.model.VehicleUpsert;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService service;

    @PostMapping
    public ResponseEntity<Void> createVehicle(@Valid @RequestBody VehicleUpsert vehicle) {
        try {
            final var createdVehicle = service.createVehicle(vehicle);
            final var location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdVehicle.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception exception) {
            throw new VehiclePersistenceException(exception);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateVehicle(@PathVariable("id") String id, @RequestBody VehicleUpsert vehicle) {
        try {
            service.updateVehicle(id, vehicle);
            return ResponseEntity.noContent().build();
        } catch (Exception exception) {
            throw new VehiclePersistenceException(exception);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable("id") String id) {
        try {
            final var vehicle = service.getVehicle(id);
            return ResponseEntity.ok(vehicle);
        } catch (VehicleNotFoundException exception) {
            throw new VehicleNotFoundException(id, exception);
        }
    }

    @GetMapping
    public ResponseEntity<RecordsPage<Vehicle>> getVehicles(@RequestParam(required = false) String vin, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        final var vehicles = service.getVehicles(vin, page, size);
        return ResponseEntity.ok(vehicles);
    }
}
