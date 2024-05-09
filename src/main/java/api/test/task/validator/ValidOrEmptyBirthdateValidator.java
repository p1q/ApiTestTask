package api.test.task.validator;

import api.test.task.annotation.ValidOrNullBirthdate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidOrEmptyBirthdateValidator implements ConstraintValidator<ValidOrNullBirthdate, LocalDate> {

    @Override
    public void initialize(ValidOrNullBirthdate constraintAnnotation) {
        // Nothing to initialize
    }

    @Override
    public boolean isValid(LocalDate birthdate, ConstraintValidatorContext context) {
        if (birthdate == null) {
            return true;
        }

        try {
            return birthdate.isBefore(LocalDate.now());
        } catch (Exception e) {
            return false;
        }
    }
}
