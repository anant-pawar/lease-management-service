package com.amg.lms.service;

import com.amg.lms.mapper.ContractMapper;
import com.amg.lms.model.Contract;
import com.amg.lms.model.ContractUpsert;
import com.amg.lms.model.RecordsPage;
import com.amg.lms.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository repository;
    private final ContractMapper mapper;

    public Contract createContract(final ContractUpsert contract) {
        final var contractEntity = mapper.map(contract);
        repository.save(contractEntity);
        final var updatedContractEntity = repository.getReferenceById(contractEntity.getId());
        return mapper.map(updatedContractEntity);
    }

    public Contract updateContract(final String id, final ContractUpsert contract) {
        final var contractEntity = mapper.map(contract, id);
        final var updatedContractEntity = repository.save(contractEntity);
        return mapper.map(updatedContractEntity);
    }

    public Contract getContract(final String id) {
        final var contract = repository.getReferenceById(id);
        return mapper.map(contract);
    }

    public RecordsPage<Contract> getContracts(final int page, final int size) {
        final var paging = PageRequest.of(page, size);
        final var pageContract = repository.findAll(paging);
        final var contractEntities = pageContract.getContent();
        final var contracts = mapper.map(contractEntities);

        return RecordsPage.<Contract>builder()
                .records(contracts)
                .totalPages(pageContract.getTotalPages())
                .currentPage(pageContract.getNumber())
                .totalItems(pageContract.getTotalElements())
                .build();
    }
}
