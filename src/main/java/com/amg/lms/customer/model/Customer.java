package com.amg.lms.customer.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Customer {
    private String id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;
}
