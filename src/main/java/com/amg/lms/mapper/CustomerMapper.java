package com.amg.lms.mapper;

import com.amg.lms.model.Customer;
import com.amg.lms.model.CustomerUpsert;
import com.amg.lms.repository.entities.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerEntity map(CustomerUpsert customer);

    @Mapping(target = "id", source = "id")
    CustomerEntity map(CustomerUpsert customer, String id);

    Customer map(CustomerEntity customer);

    List<Customer> map(List<CustomerEntity> customers);
}
