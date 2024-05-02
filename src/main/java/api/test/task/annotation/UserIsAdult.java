package api.test.task.annotation;

import api.test.task.validator.AdultValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AdultValidator.class)
public @interface UserIsAdult {
    String message() default "User must be ${min.user.age} years or older";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}
