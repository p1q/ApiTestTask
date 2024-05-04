package api.test.task.validator;

import api.test.task.annotation.ValidBirthdate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class BirthdateValidator implements ConstraintValidator<ValidBirthdate, LocalDate> {
    @Override
    public void initialize(ValidBirthdate constraintAnnotation) {
        // Nothing to initialize
    }

    @Override
    public boolean isValid(LocalDate birthdate, ConstraintValidatorContext context) {
        try {
            return birthdate.isBefore(LocalDate.now());
        } catch (Exception e) {
            return false;
        }
    }
}
