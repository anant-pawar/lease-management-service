package com.amg.lms.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class BirthDateConstraintValidator implements ConstraintValidator<BirthDateTimeConstraint, LocalDate> {
    @Override
    public boolean isValid(final LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate != null && localDate.isBefore(LocalDate.now());
    }
}
