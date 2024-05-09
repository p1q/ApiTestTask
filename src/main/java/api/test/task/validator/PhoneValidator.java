package api.test.task.validator;

import api.test.task.annotation.ValidOrNullPhone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<ValidOrNullPhone, String> {
    private static final String PHONE_PATTERN = "^$|^(\\+[0-9]{1,3})?[0-9]{9,14}$";

    @Override
    public void initialize(ValidOrNullPhone constraintAnnotation) {
        // Nothing to initialize
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null) {
            return true;
        }

        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        return pattern.matcher(phone).matches();
    }
}
