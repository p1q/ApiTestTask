package api.test.task.annotation;

import api.test.task.validator.BirthdateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthdateValidator.class)
public @interface ValidBirthdate {
    String message() default "Birthdate must be earlier than current date";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}
