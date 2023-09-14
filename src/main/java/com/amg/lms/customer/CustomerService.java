package com.amg.lms.customer;

import com.amg.lms.customer.model.Customer;
import com.amg.lms.customer.model.CustomerUpsert;
import com.amg.lms.model.RecordsPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Service class for managing customers.
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    /**
     * Create a new customer.
     *
     * @param customer The customer to create.
     * @return The created customer.
     */
    public Customer createCustomer(final CustomerUpsert customer) {
        final var customerEntity = mapper.map(customer);
        final var updatedCustomerEntity = repository.save(customerEntity);
        return mapper.map(updatedCustomerEntity);
    }

    /**
     * Update an existing customer.
     *
     * @param id       The ID of the customer to update.
     * @param customer The updated customer data.
     */
    public void updateCustomer(final String id, final CustomerUpsert customer) {
        final var customerEntity = mapper.map(customer, id);
        repository.save(customerEntity);
    }

    /**
     * Retrieve a customer by its ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return The retrieved customer.
     */
    public Customer getCustomer(final String id) {
        final var customer = repository.getReferenceById(id);
        return mapper.map(customer);
    }

    /**
     * Get a paginated list of customers based on first name and last name filters.
     *
     * @param firstName The first name filter.
     * @param lastName  The last name filter.
     * @param page      The page number.
     * @param size      The number of customers per page.
     * @return A page of customers.
     */
    public RecordsPage<Customer> getCustomers(final String firstName, final String lastName, final int page, final int size) {
        final var paging = PageRequest.of(page, size);
        final var pageCustomers = (firstName == null && lastName == null)
                ? repository.findAll(paging)
                : repository.findByFirstNameOrLastNameContaining(firstName, lastName, paging);
        final var customers = mapper.map(pageCustomers.getContent());

        return RecordsPage.<Customer>builder()
                .records(customers)
                .totalPages(pageCustomers.getTotalPages())
                .currentPage(pageCustomers.getNumber())
                .totalItems(pageCustomers.getTotalElements())
                .build();
    }
}
