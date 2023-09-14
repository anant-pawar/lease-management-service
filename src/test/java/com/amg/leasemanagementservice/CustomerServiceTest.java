package com.amg.leasemanagementservice;

import com.amg.lms.customer.CustomerEntity;
import com.amg.lms.customer.CustomerMapper;
import com.amg.lms.customer.CustomerRepository;
import com.amg.lms.customer.CustomerService;
import com.amg.lms.customer.model.Customer;
import com.amg.lms.customer.model.CustomerUpsert;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    private final CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);
    private CustomerRepository repository;
    private CustomerService service;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(CustomerRepository.class);
        service = new CustomerService(repository, MAPPER);
    }

    @Test
    @DisplayName("Create Customer Test")
    public void testCreateCustomer() {
        // Arrange
        var customerUpsert = createCustomerUpsert();
        var customerEntity = MAPPER.map(customerUpsert);
        var createdCustomerEntity = createCustomerEntity(customerUpsert);
        var expectedCustomer = MAPPER.map(createdCustomerEntity);

        Mockito.when(repository.save(customerEntity))
                .thenReturn(createdCustomerEntity);

        // Act
        Customer customer = service.createCustomer(customerUpsert);

        // Assert
        assertEquals(expectedCustomer, customer, "Created customer should match the expected customer");
        verify(repository, times(1)).save(customerEntity);
    }

    @Test
    @DisplayName("Update Customer Test")
    public void testUpdateCustomer() {
        // Arrange
        var id = UUID.randomUUID().toString();
        var customerUpsert = createCustomerUpsert();
        var customerEntity = MAPPER.map(customerUpsert, id);
        var updatedCustomerEntity = createCustomerEntity(customerUpsert);

        Mockito.when(repository.save(customerEntity))
                .thenReturn(updatedCustomerEntity);

        // Act
        service.updateCustomer(id, customerUpsert);

        // Assert
        verify(repository, times(1)).save(customerEntity);
    }

    @Test
    @DisplayName("Get Customer Test")
    public void testGetCustomer() {
        // Arrange
        var id = UUID.randomUUID().toString();
        var customerEntity = createCustomerEntity(id);
        var expectedCustomer = MAPPER.map(customerEntity);

        Mockito.when(repository.getReferenceById(id))
                .thenReturn(customerEntity);

        // Act
        var customer = service.getCustomer(id);

        // Assert
        assertEquals(expectedCustomer, customer, "Retrieved customer should match the expected customer");
        verify(repository, times(1)).getReferenceById(id);
    }

    @Test
    @DisplayName("Get Customers Test")
    public void testGetCustomers() {
        // Arrange
        var page = 0;
        var size = 1;
        var pageRequest = PageRequest.of(page, size);
        var id = UUID.randomUUID().toString();
        var customerEntity = createCustomerEntity(id);
        var expectedCustomer = MAPPER.map(customerEntity);

        when(repository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(List.of(customerEntity)));

        // Act
        var customers = service.getCustomers(null, null, page, size);

        // Assert
        assertFalse(customers.getRecords().isEmpty(), "Returned customers list should not be empty");
        assertEquals(0, customers.getCurrentPage(), "Current page should be 0");
        assertEquals(1, customers.getTotalPages(), "Total pages should be 1");
        assertEquals(1, customers.getTotalItems(), "Total items should be 1");
        assertEquals(expectedCustomer, customers.getRecords().get(0), "Retrieved customer should match the expected customer");
        verify(repository, times(1)).findAll(pageRequest);
    }

    private CustomerUpsert createCustomerUpsert() {
        var customerUpsert = new CustomerUpsert();
        customerUpsert.setFirstName("FIRST_FAKE");
        customerUpsert.setLastName("LAST_FAKE");
        return customerUpsert;
    }

    private CustomerEntity createCustomerEntity(final CustomerUpsert customerUpsert) {
        var customer = MAPPER.map(customerUpsert);
        customer.setId(UUID.randomUUID().toString());
        return customer;
    }

    private CustomerEntity createCustomerEntity(String id) {
        var customer = new CustomerEntity();
        customer.setId(id);
        customer.setFirstName("FIRST_FAKE");
        customer.setLastName("LAST_FAKE");
        return customer;
    }
}