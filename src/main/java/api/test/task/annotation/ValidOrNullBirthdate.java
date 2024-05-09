package api.test.task.annotation;

import api.test.task.validator.ValidOrEmptyBirthdateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidOrEmptyBirthdateValidator.class)
public @interface ValidOrNullBirthdate {
    String message() default "Birthdate must be valid or empty";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}
