package com.amg.lms.service;

import com.amg.lms.mapper.CustomerMapper;
import com.amg.lms.model.Customer;
import com.amg.lms.model.CustomerUpsert;
import com.amg.lms.model.RecordsPage;
import com.amg.lms.repository.CustomerRepository;
import com.amg.lms.repository.entities.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public Customer createCustomer(final CustomerUpsert customer) {
        final CustomerEntity customerEntity = mapper.map(customer);
        final CustomerEntity updatedCustomerEntity = repository.save(customerEntity);
        return mapper.map(updatedCustomerEntity);
    }

    public Customer updateCustomer(final String id, final CustomerUpsert customer) {
        final CustomerEntity customerEntity = mapper.map(customer, id);
        final CustomerEntity updatedCustomerEntity = repository.save(customerEntity);
        return mapper.map(updatedCustomerEntity);
    }

    public Customer getCustomer(final String id) {
        final CustomerEntity customer = repository.getReferenceById(id);
        return mapper.map(customer);
    }

    public RecordsPage<Customer> getCustomers(final String firstName, final String lastName, final int page, final int size) {
        final Pageable paging = PageRequest.of(page, size);
        final Page<CustomerEntity> pageCustomers = (firstName == null && lastName == null) ? repository.findAll(paging) : repository.findByFirstNameOrLastNameContaining(firstName, lastName, paging);
        final List<CustomerEntity> customerEntities = pageCustomers.getContent();
        final List<Customer> customers = mapper.map(customerEntities);

        return RecordsPage.<Customer>builder()
                .records(customers)
                .totalPages(pageCustomers.getTotalPages())
                .currentPage(pageCustomers.getNumber())
                .totalItems(pageCustomers.getTotalElements())
                .build();
    }
}
