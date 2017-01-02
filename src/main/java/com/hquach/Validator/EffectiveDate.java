package com.hquach.Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Effective Date Validator
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EffectiveDateValidator.class)
@Documented
public @interface EffectiveDate {
    String message() default "Effective date must be in this year.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
