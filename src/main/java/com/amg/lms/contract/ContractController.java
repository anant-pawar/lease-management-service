package com.amg.lms.contract;

import com.amg.lms.contract.model.Contract;
import com.amg.lms.contract.model.ContractUpsert;
import com.amg.lms.model.RecordsPage;
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
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService service;

    @PostMapping
    public ResponseEntity<Void> createContract(@Valid @RequestBody ContractUpsert contract) {
        final var createdContract = service.createContract(contract);
        final var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdContract.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateContract(@PathVariable("id") String id, @RequestBody ContractUpsert contract) {
        service.updateContract(id, contract);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContract(@PathVariable("id") String id) {
        final var contract = service.getContract(id);
        return ResponseEntity.ok(contract);
    }

    @GetMapping
    public ResponseEntity<RecordsPage<Contract>> getContracts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        final var contracts = service.getContracts(page, size);
        return ResponseEntity.ok(contracts);
    }
}

