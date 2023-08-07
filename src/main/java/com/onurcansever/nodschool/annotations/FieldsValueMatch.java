package com.onurcansever.nodschool.annotations;

import com.onurcansever.nodschool.validations.FieldsValueMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { FieldsValueMatchValidator.class })
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface FieldsValueMatch {
    String message() default "Fields do not match with each other.";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    // Accepts two fields information for the validation.
    String field();
    String fieldMatch();

    @Target(value = ElementType.TYPE)
    @Retention(value = RetentionPolicy.RUNTIME)
    @interface List {
        FieldsValueMatch[] value();
    }
}
