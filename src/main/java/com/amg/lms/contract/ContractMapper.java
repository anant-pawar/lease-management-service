package com.amg.lms.contract;

import com.amg.lms.contract.model.Contract;
import com.amg.lms.contract.model.ContractOverview;
import com.amg.lms.contract.model.ContractUpsert;
import com.amg.lms.customer.CustomerEntity;
import com.amg.lms.vehicle.VehicleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    @Mapping(target = "vehicle.id", source = "vehicleId")
    @Mapping(target = "customer.id", source = "customerId")
    @Mapping(target = "id", ignore = true)
    ContractEntity map(ContractUpsert contract);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "vehicle.id", source = "contract.vehicleId")
    @Mapping(target = "customer.id", source = "contract.customerId")
    ContractEntity map(ContractUpsert contract, String id);

    Contract map(ContractEntity contract);

    List<Contract> map(List<ContractEntity> contracts);

    @Mapping(target = "customer", source = "customer" , qualifiedByName = "toCustomer")
    @Mapping(target = "vehicle", source = "vehicle" , qualifiedByName = "toVehicle")
    @Mapping(target = "vin", source = "vehicle.vin")
    @Mapping(target = "vehiclePrice", source = "vehicle.price")
    ContractOverview mapContractsOverview(ContractEntity contract);

    List<ContractOverview> mapContractsOverview(List<ContractEntity> contracts);

    @Named("toCustomer")
    static String toCustomer(CustomerEntity customer) {
        return String.format("%s %s", customer.getFirstName(), customer.getLastName());
    }

    @Named("toVehicle")
    static String toVehicle(VehicleEntity vehicle) {
        return String.format("%s %s (%s)", vehicle.getBrand(), vehicle.getModel(), vehicle.getProductionYear());
    }
}
