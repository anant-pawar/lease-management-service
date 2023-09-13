package com.amg.lms.contoller;

import com.amg.lms.model.Customer;
import com.amg.lms.model.CustomerUpsert;
import com.amg.lms.model.RecordsPage;
import com.amg.lms.service.CustomerService;
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
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @PostMapping
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody CustomerUpsert customer) {
        final var createdCustomer = service.createCustomer(customer);
        final var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCustomer.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable("id") String id, @RequestBody CustomerUpsert customer) {
        return service.updateCustomer(id, customer);
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable("id") String id) {
        return service.getCustomer(id);
    }

    @GetMapping
    public RecordsPage getCustomers(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return service.getCustomers(firstName, lastName, page, size);
    }
}
