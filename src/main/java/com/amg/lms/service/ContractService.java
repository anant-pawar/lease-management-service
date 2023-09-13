package com.amg.lms.service;

import com.amg.lms.mapper.ContractMapper;
import com.amg.lms.model.Contract;
import com.amg.lms.model.ContractUpsert;
import com.amg.lms.model.RecordsPage;
import com.amg.lms.repository.ContractRepository;
import com.amg.lms.repository.entities.ContractEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository repository;
    private final ContractMapper mapper;

    public Contract createContract(final ContractUpsert contract) {
        final ContractEntity contractEntity = mapper.map(contract);
        repository.save(contractEntity);
        final ContractEntity updatedContractEntity = repository.getReferenceById(contractEntity.getId());
        return mapper.map(updatedContractEntity);
    }

    public Contract updateContract(final String id, final ContractUpsert contract) {
        final ContractEntity contractEntity = mapper.map(contract, id);
        final ContractEntity updatedContractEntity = repository.save(contractEntity);
        return mapper.map(updatedContractEntity);
    }

    public Contract getContract(final String id) {
        final ContractEntity contract = repository.getReferenceById(id);
        return mapper.map(contract);
    }

    public RecordsPage<Contract> getContracts(final int page, final int size) {
        final Pageable paging = PageRequest.of(page, size);
        final Page<ContractEntity> pageContract = repository.findAll(paging);
        final List<ContractEntity> contractEntities = pageContract.getContent();
        final List<Contract> contracts = mapper.map(contractEntities);

        return RecordsPage.<Contract>builder()
                .records(contracts)
                .totalPages(pageContract.getTotalPages())
                .currentPage(pageContract.getNumber())
                .totalItems(pageContract.getTotalElements())
                .build();
    }
}
