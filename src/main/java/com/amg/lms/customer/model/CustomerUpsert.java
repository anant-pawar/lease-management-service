package com.amg.lms.customer.model;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerUpsert {
    private String firstName;

    private String lastName;

    private Date birthDate;
}
