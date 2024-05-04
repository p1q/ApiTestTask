package api.test.task.validator;

import api.test.task.annotation.UserIsAdult;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public class AdultValidator implements ConstraintValidator<UserIsAdult, LocalDate> {
    @Value("${min.user.age}")
    private int minUserAge;

    @Override
    public void initialize(UserIsAdult constraintAnnotation) {
        // Nothing to initialize
    }

    @Override
    public boolean isValid(LocalDate birthdate, ConstraintValidatorContext context) {
        LocalDate today = LocalDate.now();
        LocalDate userAdultDate = today.minusYears(minUserAge);
        return birthdate.isBefore(userAdultDate);
    }
}
