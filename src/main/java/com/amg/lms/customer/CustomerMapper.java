package com.amg.lms.customer;

import com.amg.lms.customer.model.Customer;
import com.amg.lms.customer.model.CustomerUpsert;
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
