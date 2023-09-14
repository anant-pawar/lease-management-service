package com.amg.lms.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
    Page<CustomerEntity> findByFirstNameOrLastNameContaining(String firstName, String lastName, Pageable pageable);
}
