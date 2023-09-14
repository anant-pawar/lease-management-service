package com.amg.lms.contract;

import com.amg.lms.customer.CustomerEntity;
import com.amg.lms.vehicle.VehicleEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "contract")
@Data
public class ContractEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "contract_number")
    private Integer contractNumber;

    @Column(name = "monthly_rate")
    private Double monthlyRate;

    @OneToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private VehicleEntity vehicle;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customer;
}
