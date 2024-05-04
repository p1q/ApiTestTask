package api.test.task.validator;

import api.test.task.annotation.ValidOrEmptyPhone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<ValidOrEmptyPhone, String> {
    private static final String PHONE_PATTERN = "^$|^(\\+[0-9]{1,3})?[0-9]{9,14}$";

    @Override
    public void initialize(ValidOrEmptyPhone constraintAnnotation) {
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
