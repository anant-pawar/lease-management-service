package com.amg.lms.contract;

import com.amg.lms.contract.model.Contract;
import com.amg.lms.contract.model.ContractOverview;
import com.amg.lms.contract.model.ContractUpsert;
import com.amg.lms.model.RecordsPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Service class for managing contracts.
 */
@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository repository;
    private final ContractMapper mapper;

    /**
     * Create a new contract.
     *
     * @param contract The contract to create.
     * @return The created contract.
     */
    public Contract createContract(final ContractUpsert contract) {
        final var contractEntity = mapper.map(contract);
        final var createdContract = repository.save(contractEntity);
        return mapper.map(createdContract);
    }

    /**
     * Update an existing contract.
     *
     * @param id       The ID of the contract to update.
     * @param contract The updated contract data.
     */
    public void updateContract(final String id, final ContractUpsert contract) {
        final var contractEntity = mapper.map(contract, id);
        repository.save(contractEntity);
    }

    /**
     * Retrieve a contract by its ID.
     *
     * @param id The ID of the contract to retrieve.
     * @return The retrieved contract.
     */
    public Contract getContract(final String id) {
        final var contract = repository.getReferenceById(id);
        return mapper.map(contract);
    }

    /**
     * Get a paginated list of contracts.
     *
     * @param page The page number.
     * @param size The number of contracts per page.
     * @return A page of contracts.
     */
    public RecordsPage<Contract> getContracts(final int page, final int size) {
        final var paging = PageRequest.of(page, size);
        final var pageContract = repository.findAll(paging);
        final var contracts = mapper.map(pageContract.getContent());

        return RecordsPage.<Contract>builder()
                .records(contracts)
                .totalPages(pageContract.getTotalPages())
                .currentPage(pageContract.getNumber())
                .totalItems(pageContract.getTotalElements())
                .build();
    }

    /**
     * Get a paginated list of contracts overview.
     *
     * @param page The page number.
     * @param size The number of contracts per page.
     * @return A page of contracts overviews.
     */
    public RecordsPage<ContractOverview> getContractsOverview(final int page, final int size) {
        final var paging = PageRequest.of(page, size);
        final var pageContract = repository.findAll(paging);
        final var contracts = mapper.mapContractsOverview(pageContract.getContent());

        return RecordsPage.<ContractOverview>builder()
                .records(contracts)
                .totalPages(pageContract.getTotalPages())
                .currentPage(pageContract.getNumber())
                .totalItems(pageContract.getTotalElements())
                .build();
    }
}
