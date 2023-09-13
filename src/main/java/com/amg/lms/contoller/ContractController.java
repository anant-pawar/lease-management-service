package com.amg.lms.contoller;

import com.amg.lms.model.Contract;
import com.amg.lms.model.ContractUpsert;
import com.amg.lms.model.RecordsPage;
import com.amg.lms.service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService service;

    @PostMapping
    public Contract createContract(@Valid @RequestBody ContractUpsert contract) {
        return service.createContract(contract);
    }

    @PutMapping("/{id}")
    public Contract updateContract(@PathVariable("id") String id, @RequestBody ContractUpsert contract) {
        return service.updateContract(id, contract);
    }

    @GetMapping("/{id}")
    public Contract getContract(@PathVariable("id") String id) {
        return service.getContract(id);
    }

    @GetMapping
    public RecordsPage getContracts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return service.getContracts(page, size);
    }
}
