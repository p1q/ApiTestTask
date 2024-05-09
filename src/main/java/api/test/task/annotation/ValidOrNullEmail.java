package api.test.task.annotation;

import api.test.task.validator.ValidOrEmptyEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidOrEmptyEmailValidator.class)
public @interface ValidOrNullEmail {
    String message() default "Email must be valid or empty";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}
