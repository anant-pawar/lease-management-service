package com.amg.leasemanagementservice;

import com.amg.lms.contract.ContractEntity;
import com.amg.lms.contract.ContractMapper;
import com.amg.lms.contract.ContractRepository;
import com.amg.lms.contract.ContractService;
import com.amg.lms.contract.exception.ContractNotFoundException;
import com.amg.lms.contract.model.Contract;
import com.amg.lms.contract.model.ContractUpsert;
import com.amg.lms.customer.CustomerEntity;
import com.amg.lms.customer.exception.CustomerNotFoundException;
import com.amg.lms.vehicle.VehicleEntity;
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

class ContractServiceTest {

    private final ContractMapper MAPPER = Mappers.getMapper(ContractMapper.class);
    private ContractRepository repository;
    private ContractService service;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(ContractRepository.class);
        service = new ContractService(repository, MAPPER);
    }

    @Test
    @DisplayName("Create Contract Test")
    void testCreateContract() {
        // Arrange
        var contractUpsert = createContractUpsert();
        var contractEntity = MAPPER.map(contractUpsert);
        var createdContractEntity = createContractEntity(contractUpsert);
        var expectedContract = MAPPER.map(createdContractEntity);

        Mockito.when(repository.save(contractEntity))
                .thenReturn(createdContractEntity);

        // Act
        Contract contract = service.createContract(contractUpsert);

        // Assert
        assertEquals(expectedContract, contract, "Created contract should match the expected contract");
        verify(repository, times(1)).save(contractEntity);
    }

    @Test
    @DisplayName("Update Contract Test")
    void testUpdateContract() {
        // Arrange
        var id = UUID.randomUUID().toString();
        var contractUpsert = createContractUpsert();
        var contractEntity = MAPPER.map(contractUpsert, id);
        var updatedContractEntity = createContractEntity(contractUpsert);

        Mockito.when(repository.save(contractEntity))
                .thenReturn(updatedContractEntity);

        // Act
        service.updateContract(id, contractUpsert);

        // Assert
        verify(repository, times(1)).save(contractEntity);
    }

    @Test
    @DisplayName("Get Contract Test")
    void testGetContract() {
        // Arrange
        var id = UUID.randomUUID().toString();
        var contractEntity = createContractEntity(id);
        var expectedContract = MAPPER.map(contractEntity);

        Mockito.when(repository.getReferenceById(id))
                .thenReturn(contractEntity);

        // Act
        var contract = service.getContract(id);

        // Assert
        assertEquals(expectedContract, contract, "Retrieved contract should match the expected contract");
        verify(repository, times(1)).getReferenceById(id);
    }

    @Test
    @DisplayName("Get Contract ContractNotFoundException Test")
    void testGetContract_ContractNotFoundException() {
        // Arrange
        var id = UUID.randomUUID().toString();

        Mockito.when(repository.getReferenceById(id))
                .thenThrow(EntityNotFoundException.class);

        // Act
        Exception exception = assertThrows(ContractNotFoundException.class, () -> service.getContract(id));

        // Assert
        assertEquals(String.format("contract with id %s not found", id), exception.getMessage());
    }

    @Test
    @DisplayName("Get Contracts Test")
    void testGetContracts() {
        // Arrange
        var page = 0;
        var size = 1;
        var pageRequest = PageRequest.of(page, size);
        var id = UUID.randomUUID().toString();
        var contractEntity = createContractEntity(id);
        var expectedContract = MAPPER.map(contractEntity);

        when(repository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(List.of(contractEntity)));

        // Act
        var contracts = service.getContracts(page, size);

        // Assert
        assertFalse(contracts.getRecords().isEmpty(), "Returned contracts list should not be empty");
        assertEquals(0, contracts.getCurrentPage(), "Current page should be 0");
        assertEquals(1, contracts.getTotalPages(), "Total pages should be 1");
        assertEquals(1, contracts.getTotalItems(), "Total items should be 1");
        assertEquals(expectedContract, contracts.getRecords().get(0), "Retrieved contract should match the expected contract");
        verify(repository, times(1)).findAll(pageRequest);
    }

    @Test
    @DisplayName("Get Contracts Overview Test")
    void testGetContractsOverview() {
        // Arrange
        var page = 0;
        var size = 1;
        var pageRequest = PageRequest.of(page, size);
        var id = UUID.randomUUID().toString();
        var contractEntity = createContractEntity(id);
        var expectedContract = MAPPER.mapContractsOverview(contractEntity);

        when(repository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(List.of(contractEntity)));

        // Act
        var contracts = service.getContractsOverview(page, size);

        // Assert
        assertFalse(contracts.getRecords().isEmpty(), "Returned contracts list should not be empty");
        assertEquals(0, contracts.getCurrentPage(), "Current page should be 0");
        assertEquals(1, contracts.getTotalPages(), "Total pages should be 1");
        assertEquals(1, contracts.getTotalItems(), "Total items should be 1");
        assertEquals(expectedContract, contracts.getRecords().get(0), "Retrieved contract should match the expected contract");
        verify(repository, times(1)).findAll(pageRequest);
    }

    private ContractUpsert createContractUpsert() {
        var contractUpsert = new ContractUpsert();
        contractUpsert.setContractNumber(1);
        contractUpsert.setMonthlyRate(100.0);
        return contractUpsert;
    }

    private ContractEntity createContractEntity(final ContractUpsert contractUpsert) {
        var contract = MAPPER.map(contractUpsert);
        contract.setId(UUID.randomUUID().toString());
        return contract;
    }

    private ContractEntity createContractEntity(String id) {
        var contract = new ContractEntity();
        contract.setId(id);
        contract.setContractNumber(1);
        contract.setMonthlyRate(100.0);
        contract.setCustomer(createCustomerEntity());
        contract.setVehicle(createVehicleEntity());

        return contract;
    }

    public CustomerEntity createCustomerEntity() {
        var customer = new CustomerEntity();
        customer.setId(UUID.randomUUID().toString());
        customer.setFirstName("FIRST_FAKE");
        customer.setLastName("LAST_FAKE");
        return customer;
    }

    private VehicleEntity createVehicleEntity() {
        var vehicle = new VehicleEntity();
        vehicle.setId(UUID.randomUUID().toString());
        vehicle.setVin("VIN_FAKE");
        vehicle.setModel("MODEL_FAKE");
        vehicle.setBrand("BRAND_FAKE");
        vehicle.setPrice(10000.0);
        vehicle.setProductionYear(2000);
        return vehicle;
    }
}