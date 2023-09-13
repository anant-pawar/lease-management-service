package com.amg.lms.model;

import lombok.Data;

import java.util.Date;

@Data
public class Customer {
    private String id;

    private String firstName;

    private String lastName;

    private Date birthDate;
}
