package com.amg.lms.repository;

import com.amg.lms.repository.entities.VehicleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, String> {
    Page<VehicleEntity> findByVinContaining(String vin, Pageable pageable);

}
