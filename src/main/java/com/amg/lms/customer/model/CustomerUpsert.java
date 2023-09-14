package com.amg.lms.customer.model;

import com.amg.lms.validation.BirthDateTimeConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerUpsert {
    @NotBlank(message = "first name cannot be blank")
    private String firstName;

    private String lastName;

    @BirthDateTimeConstraint(message = "birth date is required, and should be before current date time")
    private LocalDate birthDate;
}
