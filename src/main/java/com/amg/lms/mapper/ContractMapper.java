package com.amg.lms.mapper;

import com.amg.lms.model.Contract;
import com.amg.lms.model.ContractUpsert;
import com.amg.lms.repository.entities.ContractEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    @Mapping(target = "vehicle.id", source = "vehicleId")
    @Mapping(target = "customer.id", source = "customerId")
    ContractEntity map(ContractUpsert contract);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "vehicle.id", source = "contract.vehicleId")
    @Mapping(target = "customer.id", source = "contract.customerId")
    ContractEntity map(ContractUpsert contract, String id);

    Contract map(ContractEntity contract);

    List<Contract> map(List<ContractEntity> contracts);
}
